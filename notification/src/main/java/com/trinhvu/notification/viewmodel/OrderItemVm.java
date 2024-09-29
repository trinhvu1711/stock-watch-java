package com.trinhvu.notification.viewmodel;

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
}
