package com.nisum.cartAndCheckout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoResponse {
    private String promoCode;
    private String promoType;
    private String description;
    private Integer amount;
}
