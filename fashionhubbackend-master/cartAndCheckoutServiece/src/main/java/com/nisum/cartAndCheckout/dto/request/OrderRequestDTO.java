package com.nisum.cartAndCheckout.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    private Integer userId;
    private LocalDateTime orderDate;
    private String orderStatus;
    private BigDecimal promoDiscount;
    private BigDecimal orderTotal;
    private String paymentMode;
    private List<OrderItemRequestDTO> orderItemRequestDTOS;
}
