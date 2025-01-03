package com.trinhvu.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trinhvu.stock.service.StockService;
import com.trinhvu.stock.viewmodel.stock.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(value = "stockSymbol", defaultValue = "", required = false) String stockSymbol,
            @RequestParam(value = "stockName", defaultValue = "", required = false) String stockName
    ) throws JsonProcessingException {
        return ResponseEntity.ok(stockService.getStocksWithFilter(pageNo, pageSize, stockSymbol, stockName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StocksGetVm> getStockById(@PathVariable Long id) {
       return ResponseEntity.ok(stockService.getStockById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StocksGetVm> updateStock(@PathVariable Long id, @RequestBody StockPutVm updatedStock) {
        stockService.updateStock(id, updatedStock);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/subtract-quantity", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> subtractStockQuantity(@RequestBody List<StockPurchaseVm> stockPurchaseVms) {
        stockService.subtractStockQuantity(stockPurchaseVms);
        return ResponseEntity.noContent().build();
    }
}
