package com.catalogservice.dto;

import lombok.Data;

@Data
public class PromoProductJoinRequestDto {
    private String promoCode;        // FK to Promo.promo_code
    private Long productId;          // FK to Product.product_id

    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}
