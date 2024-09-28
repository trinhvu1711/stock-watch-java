package com.trinhvu.payment.service;

import com.trinhvu.payment.model.Payment;
import com.trinhvu.payment.repository.PaymentRepository;
import com.trinhvu.payment.viewmodel.CapturePayment;
import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentOrderStatusVm capturePayment(@Valid CapturePayment capturePayment) {
        return null;
    }

    public Payment creatPayment(CapturePayment capturePayment) {
        return null;
    }
}
