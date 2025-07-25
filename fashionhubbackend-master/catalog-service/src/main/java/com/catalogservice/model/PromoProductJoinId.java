package com.catalogservice.model;

import java.io.Serializable;

public class PromoProductJoinId implements Serializable {
    private Long productId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoProductJoinId that = (PromoProductJoinId) o;
        return java.util.Objects.equals(productId, that.productId) &&
               java.util.Objects.equals(promoCode, that.promoCode);
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(productId, promoCode);
    }
}
