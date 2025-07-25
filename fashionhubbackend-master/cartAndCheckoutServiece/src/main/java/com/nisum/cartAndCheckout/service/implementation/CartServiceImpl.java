package com.nisum.cartAndCheckout.service.implementation;

import com.nisum.cartAndCheckout.client.ProductServiceClient;
import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.*;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.exception.InvalidQuantityException;
import com.nisum.cartAndCheckout.exception.ResourceNotFoundException;
import com.nisum.cartAndCheckout.exception.UnauthorizedCartAccessException;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import com.nisum.cartAndCheckout.mapper.CartItemMapper;
import com.nisum.cartAndCheckout.service.interfaces.CartServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartServiceInterface {

    private final ShoppingCartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductServiceClient productClient;

    public CartItemResponseDTO addToCart(CartItemRequestDTO dto) {
        // Step 1: Check if cart exists
        ShoppingCart cart = cartRepo.findByUserId(dto.getUserId())
                .orElseGet(() -> {
                    ShoppingCart newCart = ShoppingCart.builder()
                            .userId(dto.getUserId())
                            .cartTotal(BigDecimal.ZERO)
                            .createdDate(LocalDateTime.now())
                            .lastUpdatedDate(LocalDateTime.now())
                            .build();
                    return cartRepo.save(newCart);
                });

        // Step 2: Check if item already exists
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(dto.getProductId()) &&
                                item.getSku().equals(dto.getSku()) &&
                                item.getSize().equals(dto.getSize()))
                .findFirst();

        if (existingItem.isPresent()) {
            return new CartItemResponseDTO("Item already exists in cart");
        }

        // Step 3: Add item
        CartItem newItem = CartItemMapper.toCartItem(dto, cart);
        cartItemRepo.save(newItem);

        // Step 4: Update cart total and last updated time
        cart.setCartTotal(cart.getCartTotal().add(dto.getFinalPrice()));
        cart.setLastUpdatedDate(LocalDateTime.now());
        cartRepo.save(cart);

        return new CartItemResponseDTO("Item added successfully");
    }

    @Transactional(readOnly = true)
    public List<CartItemDto> getCartItemsByUserId(int userId) {
        ShoppingCart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user " + userId));

        return cart.getCartItems().stream().map(item -> {
            var product = productClient.getProductByProductId(item.getProductId());
            var productAttributes = productClient.getAttributesByProductIdAndSize(item.getProductId(), item.getSize());
            var allAttributes = productClient.getAllAttributesByProductId(item.getProductId());
            var productCategory = productClient.getCategoryBySku(item.getSku());
            var availableQuantityDto = productClient.getStockQuantityBySku(item.getSku());

            List<String> availableSizes = allAttributes.stream()
                    .map(ProductAttributesDto::getSize)
                    .distinct()
                    .collect(Collectors.toList());

            return CartItemDto.builder()
                    .cartItemId(item.getId())
                    .productName(product.getName())
                    .size(productAttributes.getSize())
                    .availableSizes(availableSizes)
                    .cartQuantity(item.getQuantity())
                    .stockQuantity(availableQuantityDto != null ? availableQuantityDto.getAvailableQuantity() : 0)
                    .unitPrice(item.getUnitPrice().doubleValue())
                    .discount(item.getDiscount().doubleValue())
                    .finalPrice(item.getFinalPrice().doubleValue())
                    .imageurl(productAttributes.getProductImage())
                    .build();
        }).collect(Collectors.toList());
    }



    @Transactional
    public UpdateCartItemDto updateCartItemQuantity(int userId, int cartItemId, int newQuantity) {
        if(newQuantity<1){
            throw new InvalidQuantityException("Quantity must be atleast 1");
        }
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(()->new ResourceNotFoundException("Cart Item not found"));
        ShoppingCart cart = item.getCart();
        if(cart.getUserId() != userId){
            throw new UnauthorizedCartAccessException("Ypu are not allowed to modify this cart");
        }

        item.setQuantity(newQuantity);
        item.setFinalPrice(BigDecimal.valueOf((item.getUnitPrice().doubleValue() - item.getDiscount().doubleValue())*newQuantity));
        cartItemRepo.save(item);


        double total = cart.getCartItems().stream().map(CartItem::getFinalPrice).mapToDouble(BigDecimal::doubleValue).sum();
        cart.setCartTotal(BigDecimal.valueOf(total));
        cart.setLastUpdatedDate(LocalDateTime.now());
        cartRepo.save(cart);

        String sku=item.getSku();
        AvailableQuantityDto stock =  productClient.getStockQuantityBySku(sku);

        return new UpdateCartItemDto("updated", stock.getAvailableQuantity());



    }

    @Transactional
    public Map<String,Object> deleteCartItem(int userId, int cartItemId) {
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(()->new ResourceNotFoundException("Cart Item not found"));

        ShoppingCart cart = item.getCart();
        if(cart.getUserId() != userId){
            throw new UnauthorizedCartAccessException("You are not allowed to modify this cart");
        }

        cart.getCartItems().remove(item);
        cartItemRepo.delete(item);

        double total = cart.getCartItems().stream().map(CartItem::getFinalPrice).mapToDouble(BigDecimal::doubleValue).sum();
        cart.setCartTotal(BigDecimal.valueOf(total));
        cart.setLastUpdatedDate(LocalDateTime.now());
        cartRepo.save(cart);

        Map<String,Object> response = new HashMap<>();
        response.put("message","deleted");
        return response;

    }

    @Transactional
    public  UpdateCartItemSizeDto updateCartItemSize(int userId,int cartItemId , String newSize){
        CartItem item = cartItemRepo.findById(cartItemId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart Item not found"));
        ShoppingCart cart =item.getCart();

        if(cart.getUserId() != userId){
            throw new UnauthorizedCartAccessException("You are not allowed to modify this cart");
        }

        int productId = item.getProductId();

        ProductAttributesDto attrDto = productClient.getAttributesByProductIdAndSize(productId,newSize);

        if(attrDto == null){
            throw new ResourceNotFoundException("Product attributes not found for given size");
        }

        ProductCategoryDto categoryDto = productClient.getCategoryBySku(attrDto.getSku());
        double discountAmount = (categoryDto != null) ?categoryDto.getDiscount() : 0;

        item.setSku(attrDto.getSku());
        item.setSize(newSize);
        item.setUnitPrice(attrDto.getPrice());
        item.setDiscount(BigDecimal.valueOf(discountAmount));
        item.setFinalPrice(BigDecimal.valueOf((attrDto.getPrice().doubleValue()-discountAmount)*item.getQuantity()));


        double total = cart.getCartItems().stream().map(CartItem::getFinalPrice).mapToDouble(BigDecimal::doubleValue).sum();
        cart.setCartTotal(BigDecimal.valueOf(total));
        cart.setLastUpdatedDate(LocalDateTime.now());
        cartRepo.save(cart);

        AvailableQuantityDto stockQty = productClient.getStockQuantityBySku(attrDto.getSku());

        return new UpdateCartItemSizeDto("Size Updated",stockQty.getAvailableQuantity(),item.getUnitPrice().doubleValue(),item.getDiscount().doubleValue(),item.getFinalPrice().doubleValue());
    }
}
