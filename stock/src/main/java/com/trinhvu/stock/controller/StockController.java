package com.trinhvu.stock.controller;

import com.trinhvu.stock.service.StockService;
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
    public ResponseEntity<StockListVm> getAllStocks() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StocksGetVm> getStockById(@PathVariable Long id) {
       return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<StocksGetVm> updateStock(@PathVariable Long id, @RequestBody StockPostVm updatedStock) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
       return null;
    }
}
