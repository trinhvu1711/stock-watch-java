package com.trinhvu.stock.service;

import com.trinhvu.stock.exception.NotFoundException;
import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.model.StockPrice;
import com.trinhvu.stock.repository.StockPriceRepository;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import static com.trinhvu.stock.utils.Constants.ErrorCode.STOCK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StockPriceService {
    private final StockRepository stockRepository;
    private final StockProducer stockProducer;
    private final StockRedisService stockRedisService;
    private final StockService stockService;
    private final AlphaVantageService alphaVantageService;
    private final StockPriceRepository stockPriceRepository;

    public StocksGetVm updateStockPrice(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(()-> new NotFoundException(STOCK_NOT_FOUND, symbol));
        List<StocksPricePostVm> stocksPricePostVms = alphaVantageService.fetchStockData(symbol);
        // Loop through each fetched price, check if it already exists in the DB, and add it if not
        for (StocksPricePostVm stocksPricePostVm : stocksPricePostVms) {
            // Check if the StockPrice for the same timestamp already exists for the stock
            Optional<StockPrice> existingPrice = stockPriceRepository.findByStockIdAndTimestamp(stock, stocksPricePostVm.timestamp());

            if (existingPrice.isEmpty()) {
                // Build the new StockPrice object and add it to the stock
                StockPrice newStockPrice = builMaindStockPrice(stocksPricePostVm, stock);
                stock.getStockPrices().add(newStockPrice);
            }
        }
        StocksGetVm stocksGetVm = StocksGetVm.fromModel(stockRepository.save(stock));
        stockProducer.sendStockPriceUpdate(stocksGetVm);
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


}
