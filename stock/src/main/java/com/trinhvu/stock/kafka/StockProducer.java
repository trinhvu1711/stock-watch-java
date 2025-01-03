package com.trinhvu.stock.kafka;

import com.trinhvu.stock.viewmodel.binanceStock.BinanceStockGetVm;
import com.trinhvu.stock.viewmodel.stock.StocksGetVm;
import com.trinhvu.stock.viewmodel.stock.StocksPriceGetVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockProducer {
    private final KafkaTemplate<String, StocksGetVm> kafkaTemplate;

    public void processStock(StocksGetVm stocks) {
        log.info("Adding stock successfully");
        Message<StocksGetVm> message = MessageBuilder
                .withPayload(stocks)
                .setHeader(TOPIC, "add-stock-topic")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendStockPriceUpdate(StocksPriceGetVm stocks) {
        log.info("Update stock price successfully");
        Message<StocksPriceGetVm> message = MessageBuilder
                .withPayload(stocks)
                .setHeader(TOPIC, "stock-price-updates")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendStockBinancePrice(BinanceStockGetVm stocks) {
        log.info("Add binance stock price successfully");
        Message<BinanceStockGetVm> message = MessageBuilder
                .withPayload(stocks)
                .setHeader(TOPIC, "binance-stock-price-updates")
                .build();
        kafkaTemplate.send(message);
    }
}
