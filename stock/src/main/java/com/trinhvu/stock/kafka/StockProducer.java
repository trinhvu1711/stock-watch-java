package com.trinhvu.stock.kafka;

import com.trinhvu.stock.viewmodel.binanceStock.BinanceStockGetVm;
import com.trinhvu.stock.viewmodel.stock.StocksGetVm;
import com.trinhvu.stock.viewmodel.stock.StocksPriceGetVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockProducer {
    private final KafkaTemplate<String, StocksGetVm> kafkaStockGetVmTemplate;
    private final KafkaTemplate<String, BinanceStockGetVm> kafkaBinanceStockGetVmTemplate;

    public void processStock(StocksGetVm stocks) {
        log.info("Adding stock successfully");
        Message<StocksGetVm> message = MessageBuilder
                .withPayload(stocks)
                .setHeader(TOPIC, "add-stock-topic")
                .build();
        kafkaStockGetVmTemplate.send(message);
    }

    public void sendStockPriceUpdate(StocksPriceGetVm stocks) {
        log.info("Update stock price successfully");
        Message<StocksPriceGetVm> message = MessageBuilder
                .withPayload(stocks)
                .setHeader(TOPIC, "stock-price-updates")
                .build();
        kafkaStockGetVmTemplate.send(message);
    }

//    send single binance stock
    public void sendStockBinancePrice(BinanceStockGetVm stocks) {
        log.info("Add binance stock price successfully");
        Message<BinanceStockGetVm> message = MessageBuilder
                .withPayload(stocks)
                .setHeader(TOPIC, "binance-stock-price-updates")
                .build();
        kafkaBinanceStockGetVmTemplate.send(message);
    }

    public void sendBatchBinancePricesAsync(List<BinanceStockGetVm> stocks) {
//        log.info("Sending batch binance stock prices asynchronously: {}", stocks.size());

        stocks.parallelStream().forEach(stock -> {
            Message<BinanceStockGetVm> message = MessageBuilder
                    .withPayload(stock)
                    .setHeader(TOPIC, "binance-stock-price-updates")
                    .build();

            // Xử lý gửi với CompletableFuture
            CompletableFuture<SendResult<String, BinanceStockGetVm>> future = kafkaBinanceStockGetVmTemplate.send(message);

            future.whenComplete((result, exception) -> {
                if (exception != null) {
                    log.error("Failed to send message for stock: {}", stock, exception);
                }
            });
        });
    }

}
