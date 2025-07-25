package com.nisum.cartAndCheckout.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(nullable = false, length = 50)
    private String sku;

    @Column(nullable = false, length = 50)
    private String size;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal discount;

    @Column(name = "final_price", nullable = false)
    private BigDecimal finalPrice;

    @Column(name = "is_saved_for_later")
    private Boolean isSavedForLater;
}
