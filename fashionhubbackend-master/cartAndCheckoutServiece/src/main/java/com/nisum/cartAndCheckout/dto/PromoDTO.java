package com.nisum.cartAndCheckout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromoDTO {
    private String promoCode;
    private Integer amount;
}
