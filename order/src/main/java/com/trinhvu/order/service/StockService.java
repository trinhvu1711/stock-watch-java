package com.trinhvu.order.service;

import com.trinhvu.order.service.client.StockOpenFeignClient;
import com.trinhvu.order.viewmodel.order.OrderItemVm;
import com.trinhvu.order.viewmodel.order.OrderVm;
import com.trinhvu.order.viewmodel.stock.StockQuantityItem;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockOpenFeignClient stockOpenFeignClient;

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handleBodilessFallback")
    public void subtractStockQuantity(OrderVm orderVm) {
        List<StockQuantityItem> stockQuantityItems = buildStockQuantityItems(orderVm.orderItemVms());
        stockOpenFeignClient.updateStatus(stockQuantityItems);
    }

    private List<StockQuantityItem> buildStockQuantityItems(Set<OrderItemVm> orderItemVms) {
        return orderItemVms.stream()
                .map(orderItemVm -> StockQuantityItem.builder()
                        .stockId(orderItemVm.stockId())
                        .quantity(orderItemVm.quantity())
                        .build()
                )
                .toList();
    }
}
