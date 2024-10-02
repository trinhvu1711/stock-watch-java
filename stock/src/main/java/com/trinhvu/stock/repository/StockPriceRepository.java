package com.trinhvu.stock.repository;

import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.model.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    Optional<StockPrice> findByStockIdAndTimestamp(Stock stockId, ZonedDateTime timestamp);
}
