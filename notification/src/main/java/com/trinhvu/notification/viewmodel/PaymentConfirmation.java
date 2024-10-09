package com.trinhvu.notification.viewmodel;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentConfirmation(
        Long orderId,
        String checkOutId,
        BigDecimal amount,
        String paymentMethod,
        String paymentStatus,
        String failureMessage,
        String email,
        CustomerVm customerVm
) {
}
