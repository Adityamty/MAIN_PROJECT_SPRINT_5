package com.nisum.cartAndCheckout.service.implementation;

import com.nisum.cartAndCheckout.dto.response.InventoryAvailabilityResponseDTO;
import com.nisum.cartAndCheckout.dto.request.OrderRequestDTO;
import com.nisum.cartAndCheckout.dto.response.OrderResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.exception.InventoryException;
import com.nisum.cartAndCheckout.exception.OrderProcessingException;
import com.nisum.cartAndCheckout.mapper.InventoryUpdateMapper;
import com.nisum.cartAndCheckout.mapper.OrderRequestMapper;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import com.nisum.cartAndCheckout.service.interfaces.CheckoutService;
import com.nisum.cartAndCheckout.validation.InventoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;
import static com.nisum.cartAndCheckout.constants.AppConstants.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CheckoutServiceImpl(
            CartItemRepository cartItemRepository,
            ShoppingCartRepository shoppingCartRepository,
            RestTemplate restTemplate
    ) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public String placeOrder(Integer userId) {

        // 1. Fetch shopping cart
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderProcessingException(CART_NOT_FOUND + userId, null));

        // 2. Fetch cart items
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new OrderProcessingException(EMPTY_CART + userId, null);
        }

        // 3. Prepare SKU list
        List<String> skuList = cartItems.stream()
                .map(CartItem::getSku)
                .collect(Collectors.toList());

        // 4. Call inventory service
        List<InventoryAvailabilityResponseDTO> inventoryAvailability = fetchInventoryAvailability(skuList);

        // 5. Validate inventory
        InventoryValidator.validateInventory(cartItems, inventoryAvailability);

        // 6. Prepare and send order request to OMS
        OrderRequestDTO orderRequest = OrderRequestMapper.toOrderRequestDTO(userId, cartItems);
        Map<String, String> orderItemIds;
        try {
            orderItemIds = placeOrderInOMS(orderRequest);  // Modified to return SKU -> orderItemId map
        } catch (OrderProcessingException ex) {
            throw ex;
        }

        if (orderItemIds == null || orderItemIds.isEmpty()) {
            throw new OrderProcessingException(ORDER_FAILED, null);
        }

        // 7. Notify inventory using per-SKU order item ID
        try {
            List<Map<String, Object>> updatePayload = InventoryUpdateMapper.toInventoryUpdatePayload(
                    cartItems, inventoryAvailability, orderItemIds
            );
            updateInventoryStock(updatePayload);
        } catch (InventoryException ex) {
            throw ex;
        }

        // 8. Return comma-separated order item IDs as a single string
        return String.join(",", orderItemIds.values());
    }



    private List<InventoryAvailabilityResponseDTO> fetchInventoryAvailability(List<String> skuList) {
        try {
            ResponseEntity<InventoryAvailabilityResponseDTO[]> response = restTemplate.postForEntity(
                    INVENTORY_AVAILABILITY_URL, skuList, InventoryAvailabilityResponseDTO[].class);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (RestClientException ex) {
            throw new InventoryException(INVENTORY_UNAVAILABLE, ex);
        }
    }

    private Map<String, String> placeOrderInOMS(OrderRequestDTO orderRequest) {
        try {
            OrderResponseDTO response = restTemplate.postForObject(
                    OMS_ORDER_URL, orderRequest, OrderResponseDTO.class
            );

            if (response == null || response.getOrderItemIds() == null || response.getOrderItemIds().isEmpty()) {
                throw new OrderProcessingException("Order placement failed", new NullPointerException());
            }
            return response.getOrderItemIds();

        } catch (RestClientException ex) {
            throw new OrderProcessingException("Failed to connect to Order Management System", ex);
        }
    }



    private void updateInventoryStock(List<Map<String, Object>> updatePayload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<Map<String, Object>>> requestEntity = new HttpEntity<>(updatePayload, headers);

            restTemplate.exchange(INVENTORY_UPDATE_URL, HttpMethod.POST, requestEntity, Void.class);
        } catch (RestClientException ex) {
            throw new InventoryException(INVENTORY_UPDATE_FAILED, ex);
        }
    }

}
