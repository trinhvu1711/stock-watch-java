package com.trinhvu.order.viewmodel.order;

import com.trinhvu.order.model.Order;
import com.trinhvu.order.model.enumeration.OrderStatus;
import com.trinhvu.order.model.enumeration.PaymentStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record OrderBriefVm(
        Long id,
        String email,
        BigDecimal totalPrice,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        ZonedDateTime createdOn
) {
    public static OrderBriefVm fromModel(Order order) {
        return new OrderBriefVm(
                order.getId(),
                order.getEmail(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getCreatedOn()
        );
    }
}
