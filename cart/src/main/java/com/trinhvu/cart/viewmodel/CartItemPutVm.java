package com.trinhvu.cart.viewmodel;

public record CartItemPutVm(
        Long cartItemId,
        int quantity,
        Long stockId,
        String userId,
        String status
) {
}
