package com.trinhvu.stock.viewmodel;

import com.trinhvu.stock.model.Stock;

public record StocksGetVm(
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
    public static StocksGetVm fromModel(Stock stock) {
        return new StocksGetVm(
                stock.getSymbol(),
                stock.getName(),
                stock.getExchange(),
                stock.getCurrentPrice(),
                stock.getOpenPrice(),
                stock.getClosePrice(),
                stock.getHighPrice(),
                stock.getLowPrice(),
                stock.getVolume()
        );
    }
}
