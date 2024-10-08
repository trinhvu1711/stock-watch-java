package com.trinhvu.payment.service.rest.client;

import com.trinhvu.payment.config.AuthenticationRequestInterceptor;
import com.trinhvu.payment.viewmodel.CheckoutPutVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "checkoutService", url = "${application.config.checkout-url}",
        configuration = { AuthenticationRequestInterceptor.class})
public interface CheckoutOpenFeignClient {
    @PutMapping("/status")
    Long updateStatus(@RequestBody CheckoutPutVm checkoutPutVm);
}