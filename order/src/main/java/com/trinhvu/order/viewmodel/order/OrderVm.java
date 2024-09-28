package com.trinhvu.order.viewmodel.order;

import com.trinhvu.order.model.Order;
import com.trinhvu.order.model.enumeration.OrderStatus;
import com.trinhvu.order.model.enumeration.PaymentStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderVm(
        Long id,
        String email,
        String note,
        int numberItem,
        BigDecimal totalPrice,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        Long paymentId,
        Set<OrderItemVm> orderItemVms
) {
    public static OrderVm fromModel(Order order) {
        Set<OrderItemVm> orderItemVms = order.getOrderItems().stream()
                .map(OrderItemVm::fromModel)
                .collect(Collectors.toSet());

        return new OrderVm(
                order.getId(),
                order.getEmail(),
                order.getNote(),
                order.getNumberItem(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getPaymentId(),
                orderItemVms
        );
    }
}
