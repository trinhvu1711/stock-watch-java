package com.trinhvu.stock.viewmodel.stock;

import java.time.LocalDateTime;

public record StocksPricePostVm(
        Double openPrice,
        Double closePrice,
        Double highPrice,
        Double lowPrice,
        Double volume,
        LocalDateTime timestamp
) {
}
