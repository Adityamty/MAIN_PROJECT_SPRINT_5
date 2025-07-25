package com.nisum.cartAndCheckout.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryAvailabilityResponseDTO {
    private String sku;
    private Integer availableQuantity;
    private Integer categoryId;
}
