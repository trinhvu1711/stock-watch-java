package com.trinhvu.order.viewmodel.order;

import com.trinhvu.order.model.Order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record OrderGetVm(
        Long id,
        BigDecimal total,
        List<OrderItemGetVm> orderItems,
        ZonedDateTime createdOn
) {
    public static OrderGetVm fromModel(Order order) {
        return new OrderGetVm(
                order.getId(),
                order.getTotalPrice(),
                OrderItemGetVm.fromModels(order.getOrderItems()),
                order.getCreatedOn()
        );
    }
}
