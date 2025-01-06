package com.trinhvu.stock.controller;

import com.trinhvu.stock.scheduler.StockPriceScheduler;
import com.trinhvu.stock.service.BinanceService;
import com.trinhvu.stock.service.StockPriceService;
import com.trinhvu.stock.viewmodel.stock.StocksGetVm;
import com.trinhvu.stock.viewmodel.stock.StocksPriceGetVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stock-price")
@RequiredArgsConstructor
public class StockPriceController {
    private final StockPriceService stockPriceService;
    private final StockPriceScheduler stockPriceScheduler;
    private final BinanceService binanceService;

    private volatile boolean isTaskRunning = false;

    @PostMapping
    public ResponseEntity<StocksGetVm> addStock(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) {
        return ResponseEntity.ok(stockPriceService.updateStockPrice(stockSymbol));
    }

    @GetMapping
    public ResponseEntity<StocksPriceGetVm> getLastPrice(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) {
        return ResponseEntity.ok(stockPriceService.getLastPrice(stockSymbol));
    }

    @PostMapping("/start-task")
    public synchronized void startTask(@RequestParam(value = "stockSymbol", required = true) String stockSymbol) {
        if (isTaskRunning) {
            System.out.println("Task already running for: " + stockSymbol);
            return;
        }

        isTaskRunning = true;

//        stockPriceScheduler.startTask(stockSymbol);
        try {
            binanceService.startFetchAllStockData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/stop-task")
    public void stopTask() {
        if (!isTaskRunning) {
            System.out.println("No task is running.");
            return;
        }
        isTaskRunning = false;
//        stockPriceScheduler.stopTask();
        binanceService.stop();
    }

}
