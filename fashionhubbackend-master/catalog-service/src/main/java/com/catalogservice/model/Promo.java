package com.catalogservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Promo")
public class Promo {
    @Id
    private String promoCode;

    private String promoType;
    private String description;
    private Integer amount;
    private String status = "Active";

    public String getPromoCode() {
        return promoCode;
    }
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
    public String getPromoType() {
        return promoType;
    }
    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
