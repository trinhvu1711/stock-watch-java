package com.trinhvu.notification.viewmodel;

import com.trinhvu.notification.model.enumeration.OrderStatus;
import com.trinhvu.notification.model.enumeration.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        Long id,
        String email,
        String note,
        int numberItem,
        BigDecimal totalPrice,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus,
        Long paymentId,
        List<OrderItemVm> orderItemVms
) {
}
