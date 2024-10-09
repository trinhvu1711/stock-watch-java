package com.trinhvu.payment.service.rest.client;

import com.trinhvu.payment.config.AuthenticationRequestInterceptor;
import com.trinhvu.payment.viewmodel.CustomerVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "customerService", url = "${application.config.customer-url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface CustomerOpenFeignClient {
    @GetMapping("/profile")
    CustomerVm getCustomerProfile();
}