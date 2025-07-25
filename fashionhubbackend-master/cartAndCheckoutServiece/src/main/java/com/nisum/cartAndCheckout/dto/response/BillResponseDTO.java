package com.nisum.cartAndCheckout.dto.response;

import com.nisum.cartAndCheckout.dto.request.BillItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponseDTO {
    private Integer userId;
    private List<BillItemDTO> items;
    private BigDecimal totalAmount;
}
