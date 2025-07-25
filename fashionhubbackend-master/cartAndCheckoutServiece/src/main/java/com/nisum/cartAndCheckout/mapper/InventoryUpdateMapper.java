package com.nisum.cartAndCheckout.mapper;

import com.nisum.cartAndCheckout.dto.response.InventoryAvailabilityResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.exception.InventoryMappingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryUpdateMapper {

    public static List<Map<String, Object>> toInventoryUpdatePayload(List<CartItem> cartItems,
                                                                     List<InventoryAvailabilityResponseDTO> inventoryAvailability,
                                                                     Map<String, String> orderItemIds) {
        return cartItems.stream()
                .map(item -> {
                    InventoryAvailabilityResponseDTO inventory = inventoryAvailability.stream()
                            .filter(i -> i.getSku().equals(item.getSku()))
                            .findFirst()
                            .orElseThrow(() -> new InventoryMappingException("SKU not found in inventory response: " + item.getSku()));

                    if (inventory.getCategoryId() == null) {
                        throw new InventoryMappingException("Category ID missing for SKU: " + item.getSku());
                    }

                    String orderItemId = orderItemIds.get(item.getSku());
                    if (orderItemId == null) {
                        throw new InventoryMappingException("Order item ID not found for SKU: " + item.getSku());
                    }

                    Map<String, Object> map = new HashMap<>();
                    map.put("sku", item.getSku());
                    map.put("quantity", item.getQuantity());
                    map.put("orderId", orderItemId); // now each item has its own orderId
                    map.put("categoryId", inventory.getCategoryId());
                    return map;
                })
                .collect(Collectors.toList());
    }

}
