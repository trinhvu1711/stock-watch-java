package com.trinhvu.payment.service;

import com.trinhvu.payment.kafka.PaymentProducer;
import com.trinhvu.payment.model.Payment;
import com.trinhvu.payment.repository.PaymentRepository;
import com.trinhvu.payment.viewmodel.CapturePayment;
import com.trinhvu.payment.viewmodel.CustomerVm;
import com.trinhvu.payment.viewmodel.PaymentConfirmation;
import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final PaymentProducer paymentProducer;


    public PaymentOrderStatusVm capturePayment(@Valid CapturePayment capturePayment) {
        Payment payment = createPayment(capturePayment);
        Long orderId = orderService.updateCheckOutStatus(capturePayment);
        PaymentOrderStatusVm paymentOrderStatusVm =
                PaymentOrderStatusVm.builder()
                        .paymentId(payment.getId())
                        .orderId(orderId)
                        .paymentStatus(payment.getPaymentStatus().name())
                        .build();

        PaymentOrderStatusVm result = orderService.updateOrderStatus(paymentOrderStatusVm);
        CustomerVm customerVm = customerService.getCustomerProfile();

        PaymentConfirmation paymentConfirmation = PaymentConfirmation.builder()
                .orderId(orderId)
                .checkOutId(payment.getCheckOutId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .failureMessage(payment.getFailureMessage())
                .email(result.email())
                .customerVm(customerVm)
                .build();

        paymentProducer.paymentConfirmation(paymentConfirmation);

        return result;
    }

    public Payment createPayment(CapturePayment capturePayment) {
        Payment payment = Payment.builder()
                .orderId(capturePayment.orderId())
                .checkOutId(capturePayment.checkOutId())
                .amount(capturePayment.amount())
                .paymentMethod(capturePayment.paymentMethod())
                .paymentStatus(capturePayment.paymentStatus())
                .failureMessage(capturePayment.failureMessage())
                .build();
        return paymentRepository.save(payment);
    }
}
