package com.trinhvu.cart.viewmodel;

import java.util.List;

public record CartDetailVm(
        Long id,
        int quantity,
        Long stockId
) {
}
