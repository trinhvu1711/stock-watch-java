package com.trinhvu.payment.service.rest.client;

import com.trinhvu.payment.config.AuthenticationRequestInterceptor;
import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderService", url = "${application.config.order-url}",
        configuration = { AuthenticationRequestInterceptor.class})
public interface OrderOpenFeignClient {
    @PutMapping("/checkouts/status")
    PaymentOrderStatusVm updateOrderStatus(@RequestBody PaymentOrderStatusVm paymentOrderStatusVm);
}