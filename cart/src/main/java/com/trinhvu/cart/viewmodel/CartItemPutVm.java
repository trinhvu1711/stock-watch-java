package com.trinhvu.cart.viewmodel;

import com.trinhvu.cart.model.CartItem;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public record CartItemPutVm(
        Long cartItemId,
        int quantity,
        Long stockId,
        String userId,
        String status
) {
    public static CartItemPutVm fromModel(CartItem cartItem, String status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String customerId = auth.getName();
        return new CartItemPutVm(cartItem.getId(), cartItem.getQuantity(), cartItem.getStockId(), customerId, status);
    }
}
