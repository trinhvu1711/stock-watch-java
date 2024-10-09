package com.trinhvu.order.service.client;

import com.trinhvu.order.config.AuthenticationRequestInterceptor;
import com.trinhvu.order.viewmodel.customer.CustomerVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "customerService", url = "${application.config.customer-url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface CustomerOpenFeignClient {
    @GetMapping("/profile")
    CustomerVm getCustomerProfile();
}