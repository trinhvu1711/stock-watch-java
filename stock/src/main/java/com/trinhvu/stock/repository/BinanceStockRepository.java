package com.trinhvu.stock.repository;

import com.trinhvu.stock.model.BinanceStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface BinanceStockRepository extends JpaRepository<BinanceStock, Long> {
    Optional<BinanceStock> findBySymbolAndEventTime(String symbol, Instant eventTime);
}
