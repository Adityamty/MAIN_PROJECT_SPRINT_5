package com.catalogservice.dto;

import lombok.Data;

@Data
public class PromoProductJoinResponseDto {
    private Long joinId;            // If you add a PK to Promotion_product_join
    private String promoCode;
    private Long productId;

    public Long getJoinId() { return joinId; }
    public void setJoinId(Long joinId) { this.joinId = joinId; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}
