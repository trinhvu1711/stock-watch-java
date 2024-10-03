package com.trinhvu.stock.controller;

import com.trinhvu.stock.scheduler.StockPriceScheduler;
import com.trinhvu.stock.service.StockPriceService;
import com.trinhvu.stock.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stock-price")
@RequiredArgsConstructor
public class StockPriceController {
    private final StockPriceService stockPriceService;
    private final StockPriceScheduler stockPriceScheduler;
    @PostMapping
    public ResponseEntity<StocksGetVm> addStock(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) {
        return ResponseEntity.ok(stockPriceService.updateStockPrice(stockSymbol));
    }

    @GetMapping
    public ResponseEntity<StocksPriceGetVm> getLastPrice(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) {
        return ResponseEntity.ok(stockPriceService.getLastPrice(stockSymbol));
    }

    @PostMapping("/start-task")
    public void startTask(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) {
        stockPriceScheduler.startTask(stockSymbol);
    }

    @PostMapping("/stop-task")
    public void stopTask() {
        stockPriceScheduler.stopTask();
    }

}
