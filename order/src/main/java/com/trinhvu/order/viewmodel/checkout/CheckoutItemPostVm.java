package com.trinhvu.order.viewmodel.checkout;

import java.math.BigDecimal;

public record CheckoutItemPostVm(
        Long stockId,
        String stockName,
        String stockSymbol,
        int quantity,
        BigDecimal price,
        String note
) {
}
