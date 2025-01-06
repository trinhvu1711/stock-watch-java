package com.trinhvu.stock.service;

import com.binance.connector.client.WebSocketStreamClient;
import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.mapper.BinanceStockGetVmMapper;
import com.trinhvu.stock.model.BinanceStock;
import com.trinhvu.stock.repository.BinanceStockRepository;
import com.trinhvu.stock.viewmodel.binanceStock.BinanceStockGetVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
public class BinanceService {
    private final WebSocketStreamClient wsStreamClient;
    private final StockProducer stockProducer;
    private final BinanceStockGetVmMapper mapper;
    private final BinanceStockRepository repository;

    private final AtomicBoolean isAllTickerStreamRunning = new AtomicBoolean(false);

//    API Key: 8kKm54pIGsIdVhr6is1NatLv2yGZ0xKaZUqf6VJxeEOQr8Gr08cyUai59MZGnQnw
//    Secret Key: V5LfMiXk287XSr7CVMu2JCEjMPwtomvKitbP5VNXByPj35OuV2VIZl6o7ulsI6Po

    public void startFetchStockData(String symbol) throws InterruptedException {
        //            System.out.println(event);
        wsStreamClient.allTickerStream(this::sendStockData);
    }

    public synchronized void startFetchAllStockData() throws InterruptedException {
        if (isAllTickerStreamRunning.get()) {
            System.out.println("All ticker stream is already running.");
            return;
        }

        isAllTickerStreamRunning.set(true);
        //            System.out.println(event);
        wsStreamClient.allTickerStream((this::sendStockData));
    }

    private void sendStockData(String event) {
        try {
            List<BinanceStockGetVm> stockList = mapper.convertToViewModelList(event);
            if (stockList == null || stockList.isEmpty()) {
                log.warn("Mapped stock list is empty for event: {}", event);
                return;
            }
            stockProducer.sendBatchBinancePricesAsync(stockList);
        } catch (Exception e) {
            log.error("Failed to convert or send stock data: {}", e.getMessage(), e);
        }
    }

    public void stop() {
        if (!isAllTickerStreamRunning.get()) {
            System.out.println("All ticker stream is not running.");
            return;
        }
        wsStreamClient.closeAllConnections();
    }

    public BinanceStock saveOrUpdateBinanceStockData(BinanceStockGetVm stockGetVm) {
        BinanceStock newStock = BinanceStockGetVm.toModel(stockGetVm);
        return repository.findBySymbolAndEventTime(newStock.getSymbol(), newStock.getEventTime())
                .map(existingStock -> updateIfNeeded(existingStock, newStock))
                .orElseGet(() -> saveNewStock(newStock));
    }

    private BinanceStock updateIfNeeded(BinanceStock existing, BinanceStock incoming) {
        if (hasSignificantChanges(existing, incoming)) {
            updateStock(existing, incoming);
//            log.info("Updated stock record: {}", existing);
            return repository.save(existing);
        }
//        log.info("No significant changes, skipping update for stock: {}", existing);
        return existing;
    }

    private BinanceStock saveNewStock(BinanceStock stock) {
//        log.info("Saving new stock record: {}", stock);
        return repository.save(stock);
    }

    private boolean hasSignificantChanges(BinanceStock existing, BinanceStock incoming) {
        BigDecimal existingPrice = new BigDecimal(existing.getCurrentPrice());
        BigDecimal incomingPrice = new BigDecimal(incoming.getCurrentPrice());
        return isSignificantChange(existingPrice, incomingPrice);
    }

    private boolean isSignificantChange(BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue == null || newValue == null) return false;
        BigDecimal difference = newValue.subtract(oldValue).abs();
        BigDecimal threshold = oldValue.multiply(BigDecimal.valueOf(0.01)).setScale(8, RoundingMode.HALF_UP);
        return difference.compareTo(threshold) > 0;
    }

    private void updateStock(BinanceStock existing, BinanceStock incoming) {
        existing.setCurrentPrice(incoming.getCurrentPrice());
        existing.setOpenPrice(incoming.getOpenPrice());
        existing.setClosePrice(incoming.getClosePrice());
        existing.setHighPrice(incoming.getHighPrice());
        existing.setLowPrice(incoming.getLowPrice());
        existing.setVolume(incoming.getVolume());
        existing.setPriceChange(incoming.getPriceChange());
        existing.setPriceChangePercent(incoming.getPriceChangePercent());
        existing.setWeightedAvgPrice(incoming.getWeightedAvgPrice());
    }


}
