package com.catalogservice.repository;

import com.catalogservice.model.PromoProductJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoProductJoinRepository extends JpaRepository<PromoProductJoin, Long> {
    List<PromoProductJoin> findByProductIdIn(List<Long> productIds);
}
