package com.trinhvu.order.viewmodel.order;

import com.trinhvu.order.model.OrderItem;
import java.math.BigDecimal;

public record OrderItemVm(
        Long id,
        Long stockId,
        String stockName,
        String stockSymbol,
        int quantity,
        BigDecimal stockPrice,
        String note,
        Long orderId
) {
    public static OrderItemVm fromModel(OrderItem orderItem) {
        return new OrderItemVm(
                orderItem.getId(),
                orderItem.getStockId(),
                orderItem.getStockName(),
                orderItem.getStockSymbol(),
                orderItem.getQuantity(),
                orderItem.getStockPrice(),
                orderItem.getNote(),
                orderItem.getOrderId().getId()
        );
    }
}
