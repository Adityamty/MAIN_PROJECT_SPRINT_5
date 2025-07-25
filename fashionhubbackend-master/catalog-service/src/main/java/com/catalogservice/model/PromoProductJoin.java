package com.catalogservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Promotion_product_join")
@IdClass(PromoProductJoinId.class) // composite key
public class PromoProductJoin {
    @Id
    private Long productId;

    @Id
    private String promoCode;

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getPromoCode() {
        return promoCode;
    }
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
