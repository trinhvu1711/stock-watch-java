package com.trinhvu.customer.service.client;

import com.trinhvu.customer.config.AuthenticationRequestInterceptor;
import com.trinhvu.customer.viewmodel.address.AddressDetailVm;
import com.trinhvu.customer.viewmodel.address.AddressPostVm;
import com.trinhvu.customer.viewmodel.address.AddressVm;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "locationService", url = "${application.config.location-url}",
        configuration = {AuthenticationRequestInterceptor.class})
@CollectionFormat(feign.CollectionFormat.CSV)
public interface LocationOpenFeignClient {
    @GetMapping("/storefront/addresses")
    List<AddressDetailVm> getAddressesByIdList(List<Long> ids);

    @GetMapping("/storefront/addresses")
    AddressDetailVm getAddressById(Long id);

    @PostMapping("/storefront/addresses")
    AddressVm createAddress(AddressPostVm addressPostVm);
}