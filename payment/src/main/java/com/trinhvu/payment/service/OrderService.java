package com.trinhvu.payment.service;

import com.trinhvu.payment.service.rest.client.CheckoutOpenFeignClient;
import com.trinhvu.payment.service.rest.client.OrderOpenFeignClient;
import com.trinhvu.payment.viewmodel.CapturePayment;
import com.trinhvu.payment.viewmodel.CheckoutPutVm;
import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderOpenFeignClient orderClient;
    private final CheckoutOpenFeignClient checkoutClient;

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handlePaymentOrderStatusFallback")
    public PaymentOrderStatusVm updateOrderStatus(PaymentOrderStatusVm paymentOrderStatusVm) {
        return orderClient.updateOrderStatus(paymentOrderStatusVm);
    }

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handleLongFallback")
    public Long updateCheckOutStatus(CapturePayment capturePayment) {
        CheckoutPutVm checkoutPutVm = new CheckoutPutVm(capturePayment.checkOutId(), capturePayment.paymentStatus().name());
        return checkoutClient.updateStatus(checkoutPutVm);
    }
}
