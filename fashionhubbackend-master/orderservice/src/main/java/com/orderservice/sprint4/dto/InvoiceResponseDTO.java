package com.orderservice.sprint4.dto;

import com.orderservice.sprint4.model.enmus.PaymentMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceResponseDTO {
    private Integer invoiceId;
    private Integer orderId;
    private String invioceNumber;
    private LocalDateTime invoiceDate;
    private PaymentMode paymentMode;
    private BigDecimal invoiceAmount;

    public InvoiceResponseDTO(){}

    public InvoiceResponseDTO(Integer invoiceId, Integer orderId, String invioceNumber, LocalDateTime invoiceDate, PaymentMode paymentMode, BigDecimal invoiceAmount) {
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.invioceNumber = invioceNumber;
        this.invoiceDate = invoiceDate;
        this.paymentMode = paymentMode;
        this.invoiceAmount = invoiceAmount;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getInvioceNumber() {
        return invioceNumber;
    }

    public void setInvioceNumber(String invioceNumber) {
        this.invioceNumber = invioceNumber;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
