package com.trinhvu.stock.service;

import com.trinhvu.stock.exception.NotFoundException;
import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.utils.Constants;
import com.trinhvu.stock.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockProducer stockProducer;

    public StocksGetVm addStock(StockPostVm stockPostVm) {
        Stock mainStock = builMaindStock(stockPostVm);
        Stock savedStock = stockRepository.save(mainStock);
        StocksGetVm stocksGetVm = StocksGetVm.fromModel(savedStock);
        stockProducer.processStock(stocksGetVm);
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

    public StockListGetVm getStocksWithFilter(int pageNo, int pageSize, String symbol, String name) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Stock> stockPage;

        stockPage = stockRepository.getStocksWithFilter(name.trim().toLowerCase(), symbol.trim(), pageable);

        List<Stock> stocks = stockPage.getContent();
        List<StockListVm> stockListVmList = stocks.stream()
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
        Stock stock = stockRepository.findById(id).orElseThrow(() ->new NotFoundException(Constants.ErrorCode.STOCK_NOT_FOUND, id));
        return StocksGetVm.fromModel(stock);
    }

    public void updateStock(Long id, StockPutVm updatedStock) {
        Stock stock = stockRepository.findById(id).orElseThrow(() ->new NotFoundException(Constants.ErrorCode.STOCK_NOT_FOUND, id));
        updateMainStockFromVm(updatedStock, stock);
        stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(() ->new NotFoundException(Constants.ErrorCode.STOCK_NOT_FOUND, id));
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
}
