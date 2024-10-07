package com.trinhvu.order.service.client;

import com.trinhvu.order.config.AuthenticationRequestInterceptor;
import com.trinhvu.order.viewmodel.stock.StockQuantityItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "stockService", url = "${application.config.stock-url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface StockOpenFeignClient {
    @PutMapping("/subtract-quantity")
    void updateStatus(@RequestBody List<StockQuantityItem> stockQuantityItem);
}