package com.trinhvu.order.service;

import com.trinhvu.order.service.client.CustomerOpenFeignClient;
import com.trinhvu.order.viewmodel.customer.CustomerVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerOpenFeignClient customerOpenFeignClient;

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handleBodilessFallback")
    public CustomerVm getCustomerProfile() {
        return customerOpenFeignClient.getCustomerProfile();
    }
}
