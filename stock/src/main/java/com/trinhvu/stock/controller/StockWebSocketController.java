package com.trinhvu.stock.controller;

import com.trinhvu.stock.model.StockPrice;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class StockWebSocketController {
    @MessageMapping("/updateStockPrice")
    @SendTo("/topic/stock-price")
    public StockPrice sendStockPrice(StockPrice stockPrice) {
        // This method will send stock price updates to connected WebSocket clients
        return stockPrice;
    }
}
