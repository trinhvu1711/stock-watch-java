package com.trinhvu.stock.viewmodel;

import java.time.ZonedDateTime;

public record StocksPricePostVm(
        Double openPrice,
        Double closePrice,
        Double highPrice,
        Double lowPrice,
        Double volume,
        ZonedDateTime timestamp
) {
}
