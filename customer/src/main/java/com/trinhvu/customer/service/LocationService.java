package com.trinhvu.customer.service;

import com.trinhvu.customer.service.client.LocationOpenFeignClient;
import com.trinhvu.customer.viewmodel.address.AddressDetailVm;
import com.trinhvu.customer.viewmodel.address.AddressPostVm;
import com.trinhvu.customer.viewmodel.address.AddressVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationOpenFeignClient locationOpenFeignClient;

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handleAddressDetailListFallback")
    public List<AddressDetailVm> getAddressesByIdList(List<Long> ids) {
        return locationOpenFeignClient.getAddressesByIdList(ids);
    }

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handleAddressDetailListFallback")
    public AddressDetailVm getAddressById(Long id) {
        return locationOpenFeignClient.getAddressById(id);
    }

    @Retry(name = "restApi")
    @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "handleAddressDetailListFallback")
    public AddressVm createAddress(AddressPostVm addressPostVm) {
        return locationOpenFeignClient.createAddress(addressPostVm);
    }

}
