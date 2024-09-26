package com.trinhvu.stock.service;

import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.viewmodel.StockListGetVm;
import com.trinhvu.stock.viewmodel.StockListVm;
import com.trinhvu.stock.viewmodel.StockPostVm;
import com.trinhvu.stock.viewmodel.StocksGetVm;
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
        return null;
    }

    public StocksGetVm updateStock(Long id, StockPostVm updatedStock) {
        return null;
    }

    public Void deleteStock(Long id) {
        return null;
    }
}
