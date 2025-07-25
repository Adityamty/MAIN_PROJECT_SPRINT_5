package com.catalogservice.dto;

import lombok.Data;

@Data
public class PromoResponseDto {
    private String promoCode;
    private String promoType;
    private String description;
    private Integer amount;
    private String status;
    private Object discount = null;

    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public String getPromoType() { return promoType; }
    public void setPromoType(String promoType) { this.promoType = promoType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Object getDiscount() { return discount; }
    public void setDiscount(Object discount) { this.discount = discount; }
}
