package com.catalogservice.controller;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
import com.catalogservice.service.PromoProductJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/promo-product-joins")
public class PromoProductJoinController {
    private final PromoProductJoinService service;

    @Autowired
    public PromoProductJoinController(PromoProductJoinService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PromoProductJoinResponseDto> save(@RequestBody PromoProductJoinRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<PromoProductJoinResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
