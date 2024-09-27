package com.trinhvu.order.viewmodel.order;

import com.trinhvu.order.model.OrderItem;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record OrderItemGetVm(
        Long id,
        Long stockId,
        String stockName,
        String stockSymbol,
        int quantity,
        BigDecimal stockPrice
) {
    public static OrderItemGetVm fromModel(OrderItem orderItem) {
        return new OrderItemGetVm(
                orderItem.getId(),
                orderItem.getStockId(),
                orderItem.getStockName(),
                orderItem.getStockSymbol(),
                orderItem.getQuantity(),
                orderItem.getStockPrice()
        );
    }

    public static List<OrderItemGetVm> fromModels(Collection<OrderItem> orderItem) {
        if(CollectionUtils.isEmpty(orderItem)){
            return Collections.emptyList();
        }
        return orderItem.stream().map(OrderItemGetVm::fromModel).toList();
    }
}
