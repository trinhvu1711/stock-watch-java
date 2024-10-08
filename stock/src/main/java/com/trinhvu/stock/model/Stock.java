package com.trinhvu.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "stocks")
public class Stock extends AbstractAuditEntity{
    @OneToMany(mappedBy = "stockId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<StockPrice> stockPrices;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String name;
    private String exchange;
    private Double currentPrice;
    private Double openPrice;
    private Double closePrice;
    private Double highPrice;
    private Double lowPrice;
    private Double volume;
    private Long availableQuantity;
    private boolean isPublished;
    private boolean isFeatured;
    private boolean isVisibleIndividually;
    private boolean stockTrackingEnabled;
}
