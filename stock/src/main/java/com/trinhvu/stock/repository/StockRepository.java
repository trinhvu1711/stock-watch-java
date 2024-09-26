package com.trinhvu.stock.repository;

import com.trinhvu.stock.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "SELECT s FROM Stock s WHERE LOWER(s.name) LIKE %:stockName% "
            + "AND (s.symbol IN :stockSymbol OR (:stockSymbol is null OR :stockSymbol = '')) "
            + "AND s.isVisibleIndividually = TRUE "
            + "AND s.isPublished = TRUE "
            + "ORDER BY s.lastModifiedOn DESC")
    Page<Stock> getStocksWithFilter(@Param("stockName") String stockName,
                                    @Param("stockSymbol") String stockSymbol,
                                    Pageable pageable);
}
