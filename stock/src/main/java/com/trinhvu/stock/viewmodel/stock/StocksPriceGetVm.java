package com.trinhvu.stock.viewmodel.stock;

import com.trinhvu.stock.model.StockPrice;

import java.time.LocalDateTime;

public record StocksPriceGetVm(
        Double openPrice,
        Double closePrice,
        Double highPrice,
        Double lowPrice,
        Double volume,
        LocalDateTime timestamp
) {
    public static StocksPriceGetVm fromModel(StockPrice stockPrice) {
        return new StocksPriceGetVm(
                stockPrice.getOpenPrice(),
                stockPrice.getClosePrice(),
                stockPrice.getHighPrice(),
                stockPrice.getLowPrice(),
                stockPrice.getVolume(),
                stockPrice.getTimestamp()
        );
    }
}
