package com.nisum.cartAndCheckout.service.interfaces;

import com.nisum.cartAndCheckout.dto.PromoDTO;

import java.util.List;

public interface PromoService {
    public List<PromoDTO> getPromosForProducts(List<Integer> productIds);
}
