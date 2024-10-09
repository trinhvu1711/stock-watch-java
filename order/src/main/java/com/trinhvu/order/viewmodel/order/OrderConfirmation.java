package com.trinhvu.order.viewmodel.order;

import com.trinhvu.order.model.Order;
import com.trinhvu.order.model.enumeration.OrderStatus;
import com.trinhvu.order.model.enumeration.PaymentStatus;
import com.trinhvu.order.viewmodel.customer.CustomerVm;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record OrderConfirmation(
        Long id,
        String email,
        String note,
        int numberItem,
        BigDecimal totalPrice,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        Long paymentId,
        Set<OrderItemVm> orderItemVms,
        CustomerVm customerVm

) {
    public static OrderConfirmation fromModel(Order order, CustomerVm customerVm) {
        Set<OrderItemVm> orderItemVms = order.getOrderItems().stream()
                .map(OrderItemVm::fromModel)
                .collect(Collectors.toSet());

        return new OrderConfirmation(
                order.getId(),
                order.getEmail(),
                order.getNote(),
                order.getNumberItem(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getPaymentId(),
                orderItemVms,
                customerVm
        );
    }
}
