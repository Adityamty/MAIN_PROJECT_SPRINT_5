package com.catalogservice.service.impl;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.mapper.PromoProductJoinMapper;
import com.catalogservice.repository.PromoProductJoinRepository;
import com.catalogservice.service.PromoProductJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoProductJoinServiceImpl implements PromoProductJoinService {
    private final PromoProductJoinRepository repo;
    private final PromoProductJoinMapper mapper;

    @Autowired
    public PromoProductJoinServiceImpl(PromoProductJoinRepository repo, PromoProductJoinMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public PromoProductJoinResponseDto save(PromoProductJoinRequestDto dto) {
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public List<PromoProductJoinResponseDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
