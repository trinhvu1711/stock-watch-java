package com.trinhvu.stock.viewmodel;

import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.model.StockPrice;

import java.util.Set;
import java.util.stream.Collectors;

public record StockGetDetailVm(
        Long id,
        String symbol,
        String name,
        String exchange,
        Double currentPrice,
        Double openPrice,
        Double closePrice,
        Double highPrice,
        Double lowPrice,
        Double volume,
        Long availableQuantity,
        boolean isPublished,
        boolean isFeatured,
        boolean isVisibleIndividually,
        boolean stockTrackingEnabled,
        Set<StocksPriceGetVm> stockPrices

) {
    public static StockGetDetailVm fromModel(Stock stock) {
        Set<StocksPriceGetVm> stocksPriceGetVmSet = stock.getStockPrices().stream()
                .map(StocksPriceGetVm::fromModel)
                .collect(Collectors.toSet());

        return new StockGetDetailVm(
                stock.getId(),
                stock.getSymbol(),
                stock.getName(),
                stock.getExchange(),
                stock.getCurrentPrice(),
                stock.getOpenPrice(),
                stock.getClosePrice(),
                stock.getHighPrice(),
                stock.getLowPrice(),
                stock.getVolume(),
                stock.getAvailableQuantity(),
                stock.isPublished(),
                stock.isFeatured(),
                stock.isVisibleIndividually(),
                stock.isStockTrackingEnabled(),
                stocksPriceGetVmSet
        );
    }
}
