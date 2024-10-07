package com.trinhvu.payment.service;

import com.trinhvu.payment.service.rest.client.CheckoutOpenFeignClient;
import com.trinhvu.payment.service.rest.client.OrderOpenFeignClient;
import com.trinhvu.payment.viewmodel.CapturePayment;
import com.trinhvu.payment.viewmodel.CheckoutPutVm;
import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderOpenFeignClient orderClient;
    private final CheckoutOpenFeignClient checkoutClient;

    public PaymentOrderStatusVm updateOrderStatus(PaymentOrderStatusVm paymentOrderStatusVm) {
        return orderClient.updateOrderStatus(paymentOrderStatusVm);
    }

    public Long updateCheckOutStatus(CapturePayment capturePayment) {
        CheckoutPutVm checkoutPutVm = new CheckoutPutVm(capturePayment.checkOutId(), capturePayment.paymentStatus().name());
        return checkoutClient.updateStatus(checkoutPutVm);
    }
}
