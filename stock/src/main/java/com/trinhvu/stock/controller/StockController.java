package com.trinhvu.stock.controller;

import com.trinhvu.stock.service.StockService;
import com.trinhvu.stock.viewmodel.StockListGetVm;
import com.trinhvu.stock.viewmodel.StockListVm;
import com.trinhvu.stock.viewmodel.StockPostVm;
import com.trinhvu.stock.viewmodel.StocksGetVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stocks")
@RequiredArgsConstructor
public class StockController    {
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StocksGetVm> addStock(@RequestBody StockPostVm stockPostVm) {
        return ResponseEntity.ok(stockService.addStock(stockPostVm));
    }

    @GetMapping
    public ResponseEntity<StockListGetVm> listStocks(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "stock-symbol", defaultValue = "", required = false) String stockSymbol,
            @RequestParam(value = "stock-name", defaultValue = "", required = false) String stockName
    ) {
        return ResponseEntity.ok(stockService.getStocksWithFilter(pageNo, pageSize, stockSymbol, stockName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StocksGetVm> getStockById(@PathVariable Long id) {
       return ResponseEntity.ok(stockService.getStockById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StocksGetVm> updateStock(@PathVariable Long id, @RequestBody StockPostVm updatedStock) {
        return ResponseEntity.ok(stockService.updateStock(id, updatedStock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
       return ResponseEntity.ok(stockService.deleteStock(id));
    }
}
