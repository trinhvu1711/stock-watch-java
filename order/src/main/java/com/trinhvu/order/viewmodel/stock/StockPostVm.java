package com.trinhvu.order.viewmodel.stock;

public record StockPostVm(
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
