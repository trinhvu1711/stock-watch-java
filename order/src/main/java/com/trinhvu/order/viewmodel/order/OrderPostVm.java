package com.trinhvu.order.viewmodel.order;

import com.trinhvu.order.model.enumeration.OrderStatus;
import com.trinhvu.order.model.enumeration.PaymentStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderPostVm(
         String email,
         String note,
         int numberItem,
         BigDecimal totalPrice,
         OrderStatus orderStatus,
         PaymentStatus paymentStatus,
         Long paymentId,
         @NotNull
         List<OrderItemPostVm> orderItem
) {
}
