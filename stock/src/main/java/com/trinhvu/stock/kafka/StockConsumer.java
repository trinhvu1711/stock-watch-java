package com.trinhvu.stock.kafka;

import com.trinhvu.stock.model.BinanceStock;
import com.trinhvu.stock.repository.BinanceStockRepository;
import com.trinhvu.stock.service.BinanceService;
import com.trinhvu.stock.service.StockService;
import com.trinhvu.stock.viewmodel.binanceStock.BinanceStockGetVm;
import com.trinhvu.stock.viewmodel.stock.StocksPriceGetVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final BinanceService binanceService;
    private final StockService stockService;

    @KafkaListener(topics = "stock-price-updates", groupId = "stock_group")
    public void consumeStockPriceUpdate(StocksPriceGetVm stocksGetVm) {
        // Send stock price update to WebSocket clients
        try {
//            log.info("Send stock price update to WebSocket clients");
//            log.info("Received message from Kafka: {}", stocksGetVm);
            messagingTemplate.convertAndSend("/topic/stock-price", stocksGetVm);
        }catch (Exception exception) {
            log.error("Thread sleep interrupted. Nested exception {}", exception.getMessage());
        }
    }

    @KafkaListener(topics = "binance-stock-price-updates", groupId = "stock_group")
    public void consumeBinanceStockPriceUpdate(BinanceStockGetVm stocksGetVm) {
        try {
//            log.info("Processing stock price update from Kafka: {}", stocksGetVm);
            BinanceStock binanceStock = binanceService.saveOrUpdateBinanceStockData(stocksGetVm);
            messagingTemplate.convertAndSend("/topic/binance-stock-price", BinanceStockGetVm.fromModel(binanceStock));
        } catch (Exception e) {
            log.error("Failed to process stock price update: {}", e.getMessage(), e);
        }
    }

}
