package com.trinhvu.order.controller;

import com.trinhvu.order.service.OrderService;
import com.trinhvu.order.viewmodel.*;
import com.trinhvu.order.viewmodel.order.OrderGetVm;
import com.trinhvu.order.viewmodel.order.OrderListVm;
import com.trinhvu.order.viewmodel.order.OrderPostVm;
import com.trinhvu.order.viewmodel.order.OrderVm;
import com.trinhvu.order.viewmodel.stock.StockListGetVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderVm> createOrder(@Valid @RequestBody OrderPostVm orderPostVm) {
        return ResponseEntity.ok(orderService.createOrder(orderPostVm));
    }

    @GetMapping
    public ResponseEntity<OrderGetVm> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @GetMapping
    public ResponseEntity<OrderListVm> getOrders(
            @RequestParam(value = "createdFrom", defaultValue = "#{new java.util.Date(1970-01-01)}", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ZonedDateTime createdFrom,
            @RequestParam(value = "createdTo", defaultValue = "#{new java.util.Date()}", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ZonedDateTime createdTo,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "email", defaultValue = "", required = false) String email,
            @RequestParam(value = "stockName", defaultValue = "", required = false) String stockName
    ) {
        return ResponseEntity.ok(orderService.getAllOrders(
                createdFrom,
                createdTo,
                pageNo,
                pageSize,
                email,
                stockName
        ));
    }
}
