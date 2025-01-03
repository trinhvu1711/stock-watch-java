package com.trinhvu.stock.viewmodel.binanceStock;

import com.trinhvu.stock.model.BinanceStock;

import java.math.BigDecimal;
import java.time.Instant;

public record BinanceStockGetVm(
        String e,               // Event type (e.g., "24hrTicker")
        Instant E,              // Event time
        String s,               // Symbol (e.g., BTCUSDT, ETHUSDT)
        BigDecimal p,           // Price change
        BigDecimal P,           // Price change percent
        BigDecimal w,           // Weighted average price
        BigDecimal c,           // Last price
        BigDecimal Q,           // Last quantity
        BigDecimal b,           // Best bid price
        BigDecimal B,           // Best bid quantity
        BigDecimal a,           // Best ask price
        BigDecimal A,           // Best ask quantity
        BigDecimal o,           // Open price
        BigDecimal h,           // High price
        BigDecimal l,           // Low price
        BigDecimal v,           // Total traded base asset volume
        BigDecimal q            // Total traded quote asset volume
) {
    public static BinanceStockGetVm fromModel(BinanceStock binanceStock) {
        return new BinanceStockGetVm(
                "24hrTicker",               // Event type (default or dynamic if needed)
                binanceStock.getEventTime(), // Event time
                binanceStock.getSymbol(),    // Symbol
                binanceStock.getPriceChange(), // Price change
                binanceStock.getPriceChangePercent(), // Price change percent
                binanceStock.getWeightedAvgPrice(),   // Weighted average price
                binanceStock.getCurrentPrice(),       // Last price
                binanceStock.getLastQuantity(),       // Last quantity
                binanceStock.getBestBidPrice(),       // Best bid price
                binanceStock.getBestBidQuantity(),    // Best bid quantity
                binanceStock.getBestAskPrice(),       // Best ask price
                binanceStock.getBestAskQuantity(),    // Best ask quantity
                binanceStock.getOpenPrice(),          // Open price
                binanceStock.getHighPrice(),          // High price
                binanceStock.getLowPrice(),           // Low price
                binanceStock.getVolume(),             // Total traded base asset volume
                null                                  // Total traded quote asset volume, adapt as needed
        );
    }
}