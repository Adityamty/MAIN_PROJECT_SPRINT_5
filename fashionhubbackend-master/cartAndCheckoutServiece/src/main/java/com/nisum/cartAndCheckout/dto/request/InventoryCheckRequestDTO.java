package com.nisum.cartAndCheckout.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryCheckRequestDTO {
    private String sku;
    private Integer categoryId;
}
