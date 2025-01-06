package com.trinhvu.stock.model;

import jakarta.persistence.*;
import lombok.*;

import java.lang.String;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "binance_stock", uniqueConstraints = {
        @UniqueConstraint(name = "symbol_event_time_unique", columnNames = {"symbol", "event_time"})
})
public class BinanceStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol; // BTCUSDT, ETHUSDT, etc.

    @Column(name = "current_price")
    private String currentPrice; // Last price (c)

    @Column(name = "open_price")
    private String openPrice; // (o)

    @Column(name = "close_price")
    private String closePrice; // Last price at close (c)

    @Column(name = "high_price")
    private String highPrice; // (h)

    @Column(name = "low_price")
    private String lowPrice; // (l)

    @Column(name = "volume")
    private String volume; // (v)

    @Column(name = "price_change")
    private String priceChange; // (p)

    @Column(name = "price_change_percent")
    private String priceChangePercent; // (P)

    @Column(name = "weighted_avg_price")
    private String weightedAvgPrice; // (w)

    @Column(name = "last_quantity")
    private String lastQuantity; // (Q)

    @Column(name = "best_bid_price")
    private String bestBidPrice; // (b)

    @Column(name = "best_bid_quantity")
    private String bestBidQuantity; // (B)

    @Column(name = "best_ask_price")
    private String bestAskPrice; // (a)

    @Column(name = "best_ask_quantity")
    private String bestAskQuantity; // (A)

    @Column(name = "event_time", nullable = false)
    private Instant eventTime; // Event time (E)

    @Column(name = "close_time")
    private Instant closeTime; // Close time
}
