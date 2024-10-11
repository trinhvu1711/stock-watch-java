package com.trinhvu.stock.kafka;

import com.trinhvu.stock.viewmodel.StocksGetVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockConsumer {
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "stock-price-updates", groupId = "stock_group")
    public void consumeStockPriceUpdate(StocksGetVm stocksGetVm) {
        // Send stock price update to WebSocket clients
        log.info("Send stock price update to WebSocket clients");
        messagingTemplate.convertAndSend("/topic/stock-price", stocksGetVm);
    }
}
