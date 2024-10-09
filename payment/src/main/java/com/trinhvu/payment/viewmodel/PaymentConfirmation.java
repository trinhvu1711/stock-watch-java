package com.trinhvu.payment.viewmodel;

import com.trinhvu.payment.model.enumeration.PaymentMethod;
import com.trinhvu.payment.model.enumeration.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentConfirmation(
        Long orderId,
        String checkOutId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        String failureMessage,
        String email,
        String customerFirstName,
        String customerLastName
) {
}
