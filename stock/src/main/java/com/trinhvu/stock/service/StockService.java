package com.trinhvu.stock.service;

import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.viewmodel.StockPostVm;
import com.trinhvu.stock.viewmodel.StocksGetVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public StocksGetVm addStock(StockPostVm stockPostVm) {
        Stock mainStock = builMaindStock(stockPostVm);
        Stock savedStock = stockRepository.save(mainStock);
        return StocksGetVm.fromModel(savedStock);
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
}
