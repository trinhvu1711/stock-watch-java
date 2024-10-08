package com.trinhvu.cart.viewmodel;

import com.trinhvu.cart.model.Cart;

public record CartListVm(
        Long id,
        String customerId
) {
    public static CartListVm fromModel(Cart cart) {
        return new CartListVm(cart.getId(), cart.getCustomerId());
    }
}
