package com.nisum.cartAndCheckout.dto.response;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Map<String, String> orderItemIds;
}
