package com.trinhvu.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trinhvu.stock.exception.NotFoundException;
import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.utils.Constants;
import com.trinhvu.stock.viewmodel.stock.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockProducer stockProducer;
    private final StockRedisService stockRedisService;

    public StocksGetVm addStock(StockPostVm stockPostVm) {
        Stock mainStock = builMaindStock(stockPostVm);
        Stock savedStock = stockRepository.save(mainStock);
        StocksGetVm stocksGetVm = StocksGetVm.fromModel(savedStock);
//        stockProducer.processStock(stocksGetVm);
        return stocksGetVm;
    }

    private Stock builMaindStock(StockPostVm stockPostVm) {
        return Stock.builder()
                .symbol(stockPostVm.symbol())
                .name(stockPostVm.name())
                .exchange(stockPostVm.exchange())
                .currentPrice(stockPostVm.currentPrice())
                .openPrice(stockPostVm.openPrice())
                .closePrice(stockPostVm.closePrice())
                .highPrice(stockPostVm.highPrice())
                .lowPrice(stockPostVm.lowPrice())
                .volume(stockPostVm.volume())
                .build();
    }

    public StockListGetVm getStocksWithFilter(int pageNo, int pageSize, String symbol, String name) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Stock> stockPage = stockRedisService.getAllStocks(symbol, name, pageable);
        if (stockPage == null || stockPage.isEmpty()) {
            stockPage = stockRepository.getStocksWithFilter(name.trim().toLowerCase(), symbol.trim(), pageable);
            // Lấy tổng số trang
            stockRedisService.saveAllProducts(
                    stockPage.getContent(),
                    symbol,
                    name,
                    pageable
            );
        }

        List<StockListVm> stockListVmList = stockPage.getContent().stream()
                .map(StockListVm::fromModel)
                .toList();

        return new StockListGetVm(
                stockListVmList,
                stockPage.getNumber(),
                stockPage.getSize(),
                stockPage.getTotalElements(),
                stockPage.getTotalPages(),
                stockPage.isLast()
        );
    }

    public StocksGetVm getStockById(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new NotFoundException(Constants.ErrorCode.STOCK_NOT_FOUND, id));
        return StocksGetVm.fromModel(stock);
    }

    public void updateStock(Long id, StockPutVm updatedStock) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new NotFoundException(Constants.ErrorCode.STOCK_NOT_FOUND, id));
        updateMainStockFromVm(updatedStock, stock);
        stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new NotFoundException(Constants.ErrorCode.STOCK_NOT_FOUND, id));
        stockRepository.deleteById(id);
    }

    private void updateMainStockFromVm(StockPutVm stockPutVm, Stock stock) {
        stock.setSymbol(stockPutVm.symbol());
        stock.setName(stockPutVm.name());
        stock.setExchange(stockPutVm.exchange());
        stock.setCurrentPrice(stockPutVm.currentPrice());
        stock.setOpenPrice(stockPutVm.openPrice());
        stock.setClosePrice(stockPutVm.closePrice());
        stock.setHighPrice(stockPutVm.highPrice());
        stock.setLowPrice(stockPutVm.lowPrice());
        stock.setVolume(stockPutVm.volume());
    }

    public void subtractStockQuantity(List<StockPurchaseVm> stockPurchaseVms) {
        partitionStockQuantityByCalculation(stockPurchaseVms, this.subtractStockQuantity());
    }

    private void partitionStockQuantityByCalculation(List<StockPurchaseVm> stockPurchaseVms, BiFunction<Long, Long, Long> calculation) {
        var stockIds = stockPurchaseVms.stream()
                .map(StockPurchaseVm::stockId)
                .toList();

        var stockQuantityItemMap = stockPurchaseVms.stream()
                .collect(Collectors.toMap(
                        StockPurchaseVm::stockId,
                        Function.identity(),
                        this::mergeStockQuantityItem
                ));

        List<Stock> stocks = this.stockRepository.findAllByIdIn(stockIds);

        stocks.forEach(stock -> {
            if (stock.isStockTrackingEnabled()) {
                long amount = getRemainAmountOfStockQuantity(stockQuantityItemMap, stock, calculation);
                stock.setAvailableQuantity(amount);
            }
        });

        this.stockRepository.saveAll(stocks);
    }

    private StockPurchaseVm mergeStockQuantityItem(StockPurchaseVm s1, StockPurchaseVm s2) {
        var q1 = s1.quantity();
        var q2 = s2.quantity();
        return new StockPurchaseVm(s1.stockId(), q1 + q2);
    }

    private Long getRemainAmountOfStockQuantity(Map<Long, StockPurchaseVm> stockQuantityItemMap,
                                                Stock stock, BiFunction<Long, Long, Long> calculation) {
        Long stockQuantity = stock.getAvailableQuantity();
        var stockItem = stockQuantityItemMap.get(stock.getId());
        Long quantity = stockItem.quantity();
        return calculation.apply(stockQuantity, quantity);
    }

    private BiFunction<Long, Long, Long> subtractStockQuantity() {
        return (totalQuantity, amount) -> {
            long result = totalQuantity - amount;
            return result < 0 ? 0 : result;
        };
    }

    public void restoreStockQuantity(List<StockPurchaseVm> stockPurchaseVms) {
        partitionStockQuantityByCalculation(stockPurchaseVms, this.restoreStockQuantity());
    }

    private BiFunction<Long, Long, Long> restoreStockQuantity() {
        return Long::sum;
    }
}
