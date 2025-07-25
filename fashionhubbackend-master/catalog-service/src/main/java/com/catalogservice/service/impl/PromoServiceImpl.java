package com.catalogservice.service.impl;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;
import com.catalogservice.mapper.PromoMapper;
import com.catalogservice.model.Promo;
import com.catalogservice.model.PromoProductJoin;
import com.catalogservice.repository.PromoProductJoinRepository;
import com.catalogservice.repository.PromoRepository;
import com.catalogservice.service.PromoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {
    private final PromoRepository repo;
    private final PromoMapper mapper;
    final PromoProductJoinRepository promoProductJoinRepo;

    @Autowired
    public PromoServiceImpl(PromoRepository repo, PromoMapper mapper, PromoProductJoinRepository promoProductJoinRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.promoProductJoinRepo = promoProductJoinRepo;
    }

    @Override
    public PromoResponseDto save(PromoRequestDto dto) {
        Promo promo = mapper.toEntity(dto);
        return mapper.toDto(repo.save(promo));
    }

    @Override
    public List<PromoResponseDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public PromoResponseDto update(Long id, PromoRequestDto dto) {
        Promo promo = repo.findById(id).orElseThrow();
        promo.setPromoCode(dto.getPromoCode());
        promo.setDescription(dto.getDescription());
        return mapper.toDto(repo.save(promo));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public PromoResponseDto updateByPromoCode(String promoCode, PromoRequestDto dto) {
        Promo promo = repo.findByPromoCode(promoCode)
            .orElseThrow(() -> new RuntimeException("Promo not found with promocode: " + promoCode));
        promo.setPromoCode(dto.getPromoCode());
        promo.setDescription(dto.getDescription());
        promo.setAmount(dto.getAmount());
        // set other fields as needed
        return mapper.toDto(repo.save(promo));
    }

    @Override
    @Transactional
    public void deleteByPromoCode(String promoCode) {
        repo.deleteByPromoCode(promoCode);
    }

    @Override
    public List<PromoResponseDto> getPromosByProductIds(List<Integer> productIds) {
        List<Long> productIdsLong = productIds.stream().map(Integer::longValue).toList();
        List<PromoProductJoin> joins = promoProductJoinRepo.findByProductIdIn(productIdsLong);
        List<String> promoCodes = joins.stream().map(PromoProductJoin::getPromoCode).toList();
        List<Promo> promos = repo.findByPromoCodeIn(promoCodes);
        return promos.stream().map(mapper::toDto).toList();
    }
}
