package com.trinhvu.payment.viewmodel;

import lombok.Builder;

@Builder
public record PaymentOrderStatusVm(
        Long orderId,
        String orderStatus,
        Long paymentId,
        String paymentStatus,
        String email
) {
}
