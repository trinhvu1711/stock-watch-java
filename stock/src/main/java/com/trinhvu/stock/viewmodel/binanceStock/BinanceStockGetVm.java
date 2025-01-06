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
                new BigDecimal(binanceStock.getPriceChange()), // Price change (converted to BigDecimal)
                new BigDecimal(binanceStock.getPriceChangePercent()), // Price change percent
                new BigDecimal(binanceStock.getWeightedAvgPrice()),   // Weighted average price
                new BigDecimal(binanceStock.getCurrentPrice()),       // Last price (converted to BigDecimal)
                new BigDecimal(binanceStock.getLastQuantity()),       // Last quantity
                new BigDecimal(binanceStock.getBestBidPrice()),       // Best bid price
                new BigDecimal(binanceStock.getBestBidQuantity()),    // Best bid quantity
                new BigDecimal(binanceStock.getBestAskPrice()),       // Best ask price
                new BigDecimal(binanceStock.getBestAskQuantity()),    // Best ask quantity
                new BigDecimal(binanceStock.getOpenPrice()),          // Open price
                new BigDecimal(binanceStock.getHighPrice()),          // High price
                new BigDecimal(binanceStock.getLowPrice()),           // Low price
                new BigDecimal(binanceStock.getVolume()),             // Total traded base asset volume
                null // Total traded quote asset volume, adapt as needed
        );
    }


    public static BinanceStock toModel(BinanceStockGetVm vm) {
        return BinanceStock.builder()
                .symbol(vm.s())
                .currentPrice(vm.c().toString())  // Ensure we convert BigDecimal back to String if needed
                .openPrice(vm.o().toString())
                .closePrice(vm.c().toString()) // Assuming "last price" as close price
                .highPrice(vm.h().toString())
                .lowPrice(vm.l().toString())
                .volume(vm.v().toString())
                .priceChange(vm.p().toString())
                .priceChangePercent(vm.P().toString())
                .weightedAvgPrice(vm.w().toString())
                .lastQuantity(vm.Q().toString())
                .bestBidPrice(vm.b().toString())
                .bestBidQuantity(vm.B().toString())
                .bestAskPrice(vm.a().toString())
                .bestAskQuantity(vm.A().toString())
                .eventTime(vm.E())
                .closeTime(null) // Close time can be added as per the data flow
                .build();
    }

}