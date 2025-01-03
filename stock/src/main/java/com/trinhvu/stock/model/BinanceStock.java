package com.trinhvu.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "binance_stock")
public class BinanceStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol; // BTCUSDT, ETHUSDT, etc.

    @Column(name = "current_price", precision = 20, scale = 8)
    private BigDecimal currentPrice; // Last price (c)

    @Column(name = "open_price", precision = 20, scale = 8)
    private BigDecimal openPrice; // (o)

    @Column(name = "close_price", precision = 20, scale = 8)
    private BigDecimal closePrice; // Last price at close (c)

    @Column(name = "high_price", precision = 20, scale = 8)
    private BigDecimal highPrice; // (h)

    @Column(name = "low_price", precision = 20, scale = 8)
    private BigDecimal lowPrice; // (l)

    @Column(name = "volume", precision = 20, scale = 8)
    private BigDecimal volume; // (v)

    @Column(name = "price_change", precision = 20, scale = 8)
    private BigDecimal priceChange; // (p)

    @Column(name = "price_change_percent", precision = 10, scale = 2)
    private BigDecimal priceChangePercent; // (P)

    @Column(name = "weighted_avg_price", precision = 20, scale = 8)
    private BigDecimal weightedAvgPrice; // (w)

    @Column(name = "last_quantity", precision = 20, scale = 8)
    private BigDecimal lastQuantity; // (Q)

    @Column(name = "best_bid_price", precision = 20, scale = 8)
    private BigDecimal bestBidPrice; // (b)

    @Column(name = "best_bid_quantity", precision = 20, scale = 8)
    private BigDecimal bestBidQuantity; // (B)

    @Column(name = "best_ask_price", precision = 20, scale = 8)
    private BigDecimal bestAskPrice; // (a)

    @Column(name = "best_ask_quantity", precision = 20, scale = 8)
    private BigDecimal bestAskQuantity; // (A)

    @Column(name = "event_time", nullable = false)
    private Instant eventTime; // Event time (E)

    @Column(name = "close_time")
    private Instant closeTime; // Close time
}
