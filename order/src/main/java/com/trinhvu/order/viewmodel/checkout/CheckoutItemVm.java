package com.trinhvu.order.viewmodel.checkout;

import com.trinhvu.order.model.CheckoutItem;

import java.math.BigDecimal;

public record CheckoutItemVm(
        Long id,
        Long stockId,
        String stockName,
        String stockSymbol,
        int quantity,
        BigDecimal price,
        String note,
        String checkoutId
) {
    public static CheckoutItemVm fromModel(CheckoutItem checkoutItem) {
        return new CheckoutItemVm(
                checkoutItem.getId(),
                checkoutItem.getStockId(),
                checkoutItem.getStockName(),
                checkoutItem.getStockSymbol(),
                checkoutItem.getQuantity(),
                checkoutItem.getPrice(),
                checkoutItem.getNote(),
                checkoutItem.getCheckoutId().getId()
        );
    }
}
