package com.trinhvu.order.service;

import com.trinhvu.order.service.client.CartOpenFeignClient;
import com.trinhvu.order.viewmodel.order.OrderItemVm;
import com.trinhvu.order.viewmodel.order.OrderVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartOpenFeignClient cartOpenFeignClient;

    public void deleteCartItem(OrderVm orderVm) {
        List<Long> stockIds = orderVm.orderItemVms().stream()
                .map(OrderItemVm::stockId)
                .toList();
        cartOpenFeignClient.deleteCartItemByIds(stockIds);
    }
}
