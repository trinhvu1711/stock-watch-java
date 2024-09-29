package com.trinhvu.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "checkout_item")
public class CheckoutItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long stockId;
    private String stockName;
    private int quantity;
    private BigDecimal price;
    private String note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkoutId", referencedColumnName = "id")
    private Checkout checkoutId;
}
