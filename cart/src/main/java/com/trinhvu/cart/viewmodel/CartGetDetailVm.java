package com.trinhvu.cart.viewmodel;

import java.util.List;

public record CartGetDetailVm(
        Long id,
        String customerId,
        Long orderId,
        List<CartDetailVm> cartItemDetailVms
) {
}
