package com.trinhvu.stock.scheduler;

import com.trinhvu.stock.exception.NotFoundException;
import com.trinhvu.stock.kafka.StockProducer;
import com.trinhvu.stock.model.Stock;
import com.trinhvu.stock.model.StockPrice;
import com.trinhvu.stock.repository.StockRepository;
import com.trinhvu.stock.service.StockPriceService;
import com.trinhvu.stock.service.StockService;
import com.trinhvu.stock.utils.Constants;
import com.trinhvu.stock.viewmodel.StocksGetVm;
import com.trinhvu.stock.viewmodel.StocksPriceGetVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Component
@Slf4j
public class StockPriceScheduler {
    private final StockPriceService stockPriceService;
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final StockProducer stockProducer;
    private static final Random rand = new SecureRandom();
    private boolean isRunning = false;
    private String symbol ="";

    @Scheduled(cron = "*/5 * * * * *")
    public void streamNewPrice(){
        if(hasTrade() && isRunning){
            Stock stock = stockRepository.findBySymbol(symbol).orElseThrow(() ->
                    new NotFoundException(Constants.ErrorCode.STOCK_NOT_FOUND, symbol));;
            StockPrice stockPrice = getNewPrice(stock);
            stock.getStockPrices().add(stockPrice);
            StocksGetVm stocksGetVm = StocksGetVm.fromModel(stockRepository.save(stock));
            stockProducer.sendStockPriceUpdate(stocksGetVm);
        }
    }

    private boolean hasTrade(){
        return rand.nextBoolean();
    }

    public void startTask(String symbol) {
        isRunning = true;
        this.symbol = symbol;
    }

    public void stopTask() {
        isRunning = false;
    }

    private StockPrice getNewPrice(Stock stock) {
        Double previousClosePrice = stock.getClosePrice();

        // Tạo biến động rất nhỏ (±1%)
        double variationPercentage = 0.01;  // Giới hạn biến động trong khoảng ±1%
        boolean sign = rand.nextBoolean();  // Xác định biến động tăng hay giảm
        Double priceChange = previousClosePrice * variationPercentage * (sign ? 1 : -1);

        Double newClosePrice = previousClosePrice + priceChange;
        Double highPrice = Math.max(previousClosePrice, newClosePrice) + (rand.nextDouble() * 0.5);  // Giới hạn biến động của highPrice ±0.5
        Double lowPrice = Math.min(previousClosePrice, newClosePrice) - (rand.nextDouble() * 0.5);   // Giới hạn biến động của lowPrice ±0.5
        Double volume = 1000 + rand.nextDouble() * 5000;

        // Làm tròn các giá trị đến 2 chữ số thập phân
        newClosePrice = BigDecimal.valueOf(newClosePrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        highPrice = BigDecimal.valueOf(highPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        lowPrice = BigDecimal.valueOf(lowPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        volume = BigDecimal.valueOf(volume).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return StockPrice.builder()
                .openPrice(previousClosePrice)  // Giá mở cửa là giá đóng cửa trước đó
                .closePrice(newClosePrice)      // Giá đóng cửa là giá mới
                .highPrice(highPrice)           // Giá cao nhất có giới hạn ±0.5 đơn vị
                .lowPrice(lowPrice)             // Giá thấp nhất có giới hạn ±0.5 đơn vị
                .volume(volume)                 // Volume đã được làm tròn
                .timestamp(LocalDateTime.now())  // Thời gian hiện tại
                .stockId(stock)
                .build();
    }
}
