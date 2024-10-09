package com.trinhvu.order.service;

import com.trinhvu.order.service.client.CartOpenFeignClient;
import com.trinhvu.order.viewmodel.order.OrderItemVm;
import com.trinhvu.order.viewmodel.order.OrderVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartOpenFeignClient cartOpenFeignClient;

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handleBodilessFallback")
    public void deleteCartItem(OrderVm orderVm) {
        List<Long> stockIds = orderVm.orderItemVms().stream()
                .map(OrderItemVm::stockId)
                .toList();
        cartOpenFeignClient.deleteCartItemByIds(stockIds);
    }
}
