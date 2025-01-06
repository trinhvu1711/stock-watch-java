//package com.trinhvu.stock.controller;
//
//import com.trinhvu.stock.model.BinanceStock;
//import com.trinhvu.stock.repository.BinanceStockRepository;
//import com.trinhvu.stock.viewmodel.binanceStock.BinanceStockGetVm;
//import com.trinhvu.stock.viewmodel.stock.StocksPriceGetVm;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Slf4j
//@Controller
//@AllArgsConstructor
//public class StockWebSocketController {
//    private final BinanceStockRepository repository;
//
//    @MessageMapping("/updateStockPrice")
//    @SendTo("/topic/stock-price")
//    public StocksPriceGetVm sendStockPrice(StocksPriceGetVm stockPrice) {
//        // This method will send stock price updates to connected WebSocket clients
//        log.info("Stock price received: {}", stockPrice);
//        return stockPrice;
//    }
//
//    @MessageMapping("/updateBinanceStockPrice")
//    @SendTo("/topic/binance-stock-price")
//    public BinanceStockGetVm sendBinanceStockPrice(BinanceStockGetVm stockPrice) {
//        // This method will send stock price updates to connected WebSocket clients
//        log.info("Binance Stock price received: {}", stockPrice);
//        BinanceStock entity = BinanceStockGetVm.toModel(stockPrice);
//        try {
//            repository.save(entity);
//        } catch (Exception e) {
//            log.error("Error saving stock price: ", e);
//        }
//        return stockPrice;
//    }
//}
