package com.nisum.cartAndCheckout.mapper;

import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.dto.response.PromoResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PromoMapper {

    public static List<PromoDTO> toPromoDTOList(List<PromoResponse> promoResponses) {
        return promoResponses.stream()
                .map(promo -> {
                    PromoDTO dto = new PromoDTO();
                    dto.setPromoCode(promo.getPromoCode());
                    dto.setAmount(promo.getAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
