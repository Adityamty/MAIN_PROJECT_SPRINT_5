package com.nisum.cartAndCheckout.service.implementation;

import com.nisum.cartAndCheckout.constants.AppConstants;
import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.dto.response.PromoResponse;
import com.nisum.cartAndCheckout.exception.PromoServiceUnavailableException;
import com.nisum.cartAndCheckout.mapper.PromoMapper;
import com.nisum.cartAndCheckout.service.interfaces.PromoService;
import com.nisum.cartAndCheckout.validation.PromoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class PromoServiceImpl implements PromoService {

    private final RestTemplate restTemplate;

    @Autowired
    public PromoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<PromoDTO> getPromosForProducts(List<Integer> productIds) {
        try {
            PromoValidator.validateProductIds(productIds);

            String url = AppConstants.PROMO_SERVICE_URL;
            HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(productIds);

            ResponseEntity<List<PromoResponse>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<PromoResponse>>() {}
            );

            List<PromoResponse> promoResponseList = Objects.requireNonNull(responseEntity.getBody());
            return PromoMapper.toPromoDTOList(promoResponseList);

        } catch (IllegalArgumentException e) {
            // Wrap validation exception into PromoServiceUnavailableException
            throw new PromoServiceUnavailableException("Promo fetch failed.", e);
        } catch (Exception e) {
            throw new PromoServiceUnavailableException("Promo fetch failed.", e);
        }
    }

}
