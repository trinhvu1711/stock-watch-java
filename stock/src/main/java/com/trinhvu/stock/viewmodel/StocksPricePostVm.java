package com.trinhvu.stock.viewmodel;

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
