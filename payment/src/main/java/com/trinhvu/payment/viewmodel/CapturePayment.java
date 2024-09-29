package com.trinhvu.payment.viewmodel;

import com.trinhvu.payment.model.enumeration.PaymentMethod;
import com.trinhvu.payment.model.enumeration.PaymentStatus;

import java.math.BigDecimal;

public record CapturePayment(
        Long orderId,
        Long checkOutId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        String failureMessage
) {
}
