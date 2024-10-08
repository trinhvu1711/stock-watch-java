package com.trinhvu.order.service.client;

import com.trinhvu.order.config.AuthenticationRequestInterceptor;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "cartService", url = "${application.config.cart-url}",
        configuration = {AuthenticationRequestInterceptor.class})
@CollectionFormat(feign.CollectionFormat.CSV)
public interface CartOpenFeignClient {
    @DeleteMapping("/cart-item/multi-delete")
    void deleteCartItemByIds(@RequestParam("stockIds") List<Long> stockIds);
}