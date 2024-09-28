package com.trinhvu.order.service;

import com.trinhvu.order.viewmodel.order.OrderItemVm;
import com.trinhvu.order.viewmodel.order.OrderVm;
import com.trinhvu.order.viewmodel.stock.StockQuantityItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class StockService {
    @Value("${application.config.stock-url}")
    private String url;
    private final RestTemplate restTemplate;
    public void subtractStockQuantity(OrderVm orderVm){
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<List<StockQuantityItem>> requestEntity = new HttpEntity<>(buildStockQuantityItems(orderVm.orderItemVms()) ,headers);
        ParameterizedTypeReference<Void>responseType = new ParameterizedTypeReference<>() {};
        restTemplate.exchange(
            url + "/subtract-quantity",
                PUT,
                requestEntity,
                responseType
        );
    }

    private List<StockQuantityItem> buildStockQuantityItems(Set<OrderItemVm> orderItemVms){
        return orderItemVms.stream()
                .map(orderItemVm -> StockQuantityItem.builder()
                        .stockId(orderItemVm.stockId())
                        .quantity(orderItemVm.quantity())
                        .build()
                )
                .toList();
    }
}
