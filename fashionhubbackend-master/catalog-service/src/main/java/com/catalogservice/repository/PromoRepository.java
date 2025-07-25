package com.catalogservice.repository;

import com.catalogservice.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
    Optional<Promo> findByPromoCode(String promoCode);
    void deleteByPromoCode(String promoCode);
    List<Promo> findByPromoCodeIn(List<String> promoCodes);
}
