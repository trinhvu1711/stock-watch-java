package com.trinhvu.cart.viewmodel;

import com.trinhvu.cart.model.CartItem;

import java.util.List;

public record CartDetailVm(
        Long id,
        int quantity,
        Long stockId
) {
    public static CartDetailVm fromModel(CartItem cartItem) {
        return new CartDetailVm(cartItem.getId(), cartItem.getQuantity(), cartItem.getStockId());
    }
}
