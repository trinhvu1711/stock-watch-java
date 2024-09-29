package com.trinhvu.order.viewmodel.checkout;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CheckoutPostVm(
        String email,
        String note,
        @NotNull
        List<CheckoutItemPostVm> checkoutItemPostVms
) {
}
