package com.trinhvu.order.service;

import com.trinhvu.order.repository.OrderRepository;
import com.trinhvu.order.viewmodel.order.OrderGetVm;
import com.trinhvu.order.viewmodel.order.OrderPostVm;
import com.trinhvu.order.viewmodel.stock.StockListGetVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderGetVm createOrder(@Valid OrderPostVm orderPostVm) {
        return null;
    }

    public OrderGetVm getMyOrders() {
        return null;
    }

    public StockListGetVm getAllOrders(ZonedDateTime createdFrom, ZonedDateTime createdTo, int pageNo, int pageSize, String email, String stockName) {
        return null;
    }
}
