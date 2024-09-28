package com.trinhvu.order.viewmodel.order;

import java.math.BigDecimal;

public record OrderItemPostVm(
        Long stockId,
        String stockName,
        String stockSymbol,
        int quantity,
        BigDecimal stockPrice,
        String note
) {
}
