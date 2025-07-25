package com.nisum.productmanagement.repository;

import com.nisum.productmanagement.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategoryId(Long categoryId);


    @Query("SELECT COUNT(p) FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    int countSearchedProducts(@Param("term") String searchTerm);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.lastModifiedDate >= :threshold")
    int countRecentlyModifiedProducts(@Param("threshold") LocalDateTime threshold);

    @Query("SELECT p FROM Product p WHERE p.status = :status")
    Page<Product> findByStatus(String status, Pageable pageable);

    // Remove the ORDER BY clause from the query
    @Query("SELECT p FROM Product p")
    Page<Product> findAllOrderByLastModifiedDesc(Pageable pageable);
}
