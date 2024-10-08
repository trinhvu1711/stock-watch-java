package com.trinhvu.cart.viewmodel;

import com.trinhvu.cart.model.Cart;

import java.util.List;

public record CartGetDetailVm(
        Long id,
        String customerId,
        Long orderId,
        List<CartDetailVm> cartItemDetailVms
) {
    public static CartGetDetailVm fromModel(Cart cart) {
        return new CartGetDetailVm(
                cart.getId(),
                cart.getCustomerId(),
                cart.getOrderId(),
                cart.getCartItems().stream().map(CartDetailVm::fromModel).toList()
        );
    }
}
