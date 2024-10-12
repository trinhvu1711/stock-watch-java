package com.trinhvu.stock.service;

import com.trinhvu.stock.exception.NotFoundException;
import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.model.StockPrice;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.trinhvu.stock.utils.Constants.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StockPriceService {
    private final StockRepository stockRepository;
    private final StockProducer stockProducer;
    private final StockRedisService stockRedisService;
    private final AlphaVantageService alphaVantageService;

    public StocksGetVm updateStockPrice(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(() -> new NotFoundException(STOCK_NOT_FOUND, symbol));
        List<StocksPricePostVm> stocksPricePostVms = alphaVantageService.fetchStockData(symbol, stock);
        if (stocksPricePostVms.isEmpty()) {
            throw new NotFoundException(STOCK_HAS_NO_UPDATED_PRICE, symbol);
        }
        // Loop through each fetched price, check if it already exists in the DB, and add it if not
        for (StocksPricePostVm stocksPricePostVm : stocksPricePostVms) {
            System.out.println(stocksPricePostVm);
            // Build the new StockPrice object and add it to the stock
            StockPrice newStockPrice = builMaindStockPrice(stocksPricePostVm, stock);
            stock.getStockPrices().add(newStockPrice);
        }

        StocksGetVm stocksGetVm = StocksGetVm.fromModel(stockRepository.save(stock));
        stockProducer.processStock(stocksGetVm);
        return stocksGetVm;
    }

    private StockPrice builMaindStockPrice(StocksPricePostVm stockPostVm, Stock stock) {
        return StockPrice.builder()
                .stockId(stock)
                .openPrice(stockPostVm.openPrice())
                .closePrice(stockPostVm.closePrice())
                .highPrice(stockPostVm.highPrice())
                .lowPrice(stockPostVm.lowPrice())
                .volume(stockPostVm.volume())
                .timestamp(stockPostVm.timestamp())
                .build();
    }


    public StocksPriceGetVm getLastPrice(String stockSymbol) {
        return null;
    }

    public StocksPriceGetVm addStockPrice(StocksPricePostVm stockPostVm) {
        return null;
    }
}
