package com.trinhvu.stock.controller;

import com.trinhvu.stock.model.StockPrice;
import com.trinhvu.stock.viewmodel.StocksPriceGetVm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StockWebSocketController {
    @MessageMapping("/updateStockPrice")
    @SendTo("/topic/stock-price")
    public StocksPriceGetVm sendStockPrice(StocksPriceGetVm stockPrice) {
        // This method will send stock price updates to connected WebSocket clients
        log.info("Stock price received: {}", stockPrice);
        return stockPrice;
    }
}
