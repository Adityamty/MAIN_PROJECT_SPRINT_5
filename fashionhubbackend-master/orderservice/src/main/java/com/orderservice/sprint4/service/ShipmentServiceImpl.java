package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dao.ShipmentItemDAO;
import com.orderservice.sprint4.dto.*;
import com.orderservice.sprint4.exception.*;
import com.orderservice.sprint4.model.Order;
import com.orderservice.sprint4.repository.OrderInvoiceRepository;
import com.orderservice.sprint4.repository.OrderRepository;
import com.orderservice.sprint4.repository.custom.CustomShipmentItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderInvoiceRepository orderInvoiceRepository;

    @Autowired
    private CustomShipmentItemRepository customShipmentItemRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.service.product.data.url}")
    private String PRODUCT_SERVICE_PRODUCT_DATA_URL;

    @Value("${product.service.seller.data.url}")
    private String PRODUCT_SERVICE_SELLER_DATA_URL;

    @Override
    public ShipmentDetailsResponseDTO getShipmentItemsByOrderId(Integer orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        List<ShipmentItemDAO> items = customShipmentItemRepository.getShipmentItemsByOrderId(orderId);
        if (items == null || items.isEmpty()) {
            throw new ShipmentItemsNotFoundException(orderId);
        }
        List<ShipmentItemDTO> itemDTO = new ArrayList<>();

        items.stream().forEach(item ->{
            Integer productId = item.getProduct();
            String productName = getProductName(productId);

            String sellerName = getSellerName(productId);

            ShipmentItemDTO itm = new ShipmentItemDTO();
            itm.setProduct(productName);
            itm.setTrackingId(item.getTrackingId());
            itm.setSku(item.getSku());
            itm.setQuantity(item.getQuantity());
            itm.setItemStatus(item.getItemStatus());
            itm.setShipmentDate(item.getShipmentDate());
            itm.setDeliveredDate(item.getDeliveredDate());
            itm.setSeller(sellerName);




            itemDTO.add(itm);
        });

        InvoiceResponseDTO invoiceResponseDTO = orderInvoiceRepository.getInvoiceByOrderId(orderId);
        if (invoiceResponseDTO == null) {
            throw new InvoiceNotFoundException(orderId);
        }


        ShipmentDetailsResponseDTO dto = new ShipmentDetailsResponseDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setPaymentMode(invoiceResponseDTO.getPaymentMode());
        dto.setItems(itemDTO);
        return dto;
    }

    public String getSellerName(Integer id) {
        if (id == null) return null;

        try {
            ResponseEntity<SellerDTO> response = restTemplate.getForEntity(
                    PRODUCT_SERVICE_SELLER_DATA_URL + id + "/sellers", SellerDTO.class);

            SellerDTO body = response.getBody();
            if (response.getStatusCode().isError() || body == null || body.getSellerName() == null || body.getSellerName().isBlank()) {
                throw new RuntimeException("Product service error or empty seller name");
            }

            return body.getSellerName();

        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Product service failed: " + e.getMessage());
        } catch (ResourceAccessException e) {
            throw new ExternalServiceException("Unable to reach product service: " + e.getMessage());
        } catch (Exception e) {
            throw new ExternalServiceException("Unexpected error during product: " + e.getMessage());
        }
    }

    public String getProductName(Integer id) {
        if (id == null) return null;

        try {
            ResponseEntity<ProductDTO> response = restTemplate.getForEntity(
                    PRODUCT_SERVICE_PRODUCT_DATA_URL + id, ProductDTO.class);

            ProductDTO body = response.getBody();
            if (response.getStatusCode().isError() || body == null || body.getName() == null || body.getName().isBlank()) {
                throw new RuntimeException("Product service error or name not found");
            }

            return body.getName();

        } catch (HttpServerErrorException e) {
            throw new ExternalServiceException("Product service failed: " + e.getMessage());
        } catch (ResourceAccessException e) {
            throw new ExternalServiceException("Unable to reach product service: " + e.getMessage());
        } catch (Exception e) {
            throw new ExternalServiceException("Unexpected error during product: " + e.getMessage());
        }
    }
}
