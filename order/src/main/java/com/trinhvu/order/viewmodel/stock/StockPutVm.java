package com.trinhvu.order.viewmodel.stock;

public record StockPutVm(
        String symbol,
        String name,
        String exchange,
        Double currentPrice,
        Double openPrice,
        Double closePrice,
        Double highPrice,
        Double lowPrice,
        Double volume
) {
}
