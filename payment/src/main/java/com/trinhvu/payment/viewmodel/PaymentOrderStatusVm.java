package com.trinhvu.payment.viewmodel;

public record PaymentOrderStatusVm(
        Long orderId,
        String orderStatus,
        Long paymentId,
        String paymentStatus
) {
}
