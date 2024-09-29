package com.trinhvu.order.viewmodel.checkout;

import com.trinhvu.order.model.Checkout;
import com.trinhvu.order.model.enumeration.CheckoutState;

import java.util.Set;
import java.util.stream.Collectors;

public record CheckoutVm(
        String id,
        String email,
        String note,
        CheckoutState checkoutState,
        Set<CheckoutItemVm> checkoutItemSet
) {
    public static CheckoutVm fromModel(Checkout checkout) {
        Set<CheckoutItemVm> checkoutItemSet = checkout.getCheckoutItemSet().stream()
                .map(CheckoutItemVm::fromModel)
                .collect(Collectors.toSet());

        return new CheckoutVm(
                checkout.getId(),
                checkout.getEmail(),
                checkout.getNote(),
                checkout.getCheckoutState(),
                checkoutItemSet
        );
    }
}
