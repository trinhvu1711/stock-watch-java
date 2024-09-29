package com.trinhvu.order.service;

import com.trinhvu.order.exception.NotFoundException;
import com.trinhvu.order.model.Checkout;
import com.trinhvu.order.model.CheckoutItem;
import com.trinhvu.order.model.Order;
import com.trinhvu.order.model.enumeration.CheckoutState;
import com.trinhvu.order.repository.CheckoutRepository;
import com.trinhvu.order.viewmodel.checkout.CheckoutPostVm;
import com.trinhvu.order.viewmodel.checkout.CheckoutPutVm;
import com.trinhvu.order.viewmodel.checkout.CheckoutVm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.trinhvu.order.utils.Constants.ErrorCode.CHECKOUT_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutService {
    private final CheckoutRepository checkoutRepository;
    private final OrderService orderService;

    public CheckoutVm createCheckOut(CheckoutPostVm checkoutPostVm) {
        Checkout checkout = Checkout.builder()
                .email(checkoutPostVm.email())
                .note(checkoutPostVm.note())
                .checkoutItemSet(checkoutPostVm.checkoutItemPostVms().stream()
                        .map(checkoutItemPostVm -> CheckoutItem.builder()
                                .stockId(checkoutItemPostVm.stockId())
                                .stockName(checkoutItemPostVm.stockName())
                                .quantity(checkoutItemPostVm.quantity())
                                .price(checkoutItemPostVm.price())
                                .note(checkoutItemPostVm.note())
                                .build()
                        ).collect(Collectors.toSet())
                )
                .build();
        checkout.setCheckoutState(CheckoutState.PENDING);
        checkout.setId(UUID.randomUUID().toString());
        checkout.getCheckoutItemSet().forEach(checkoutItem -> checkoutItem.setCheckoutId(checkout));
        return CheckoutVm.fromModel(checkoutRepository.save(checkout));
    }

    public Long updateCheckoutStatus(CheckoutPutVm checkoutPutVm) {
        Checkout checkout = checkoutRepository.findById(checkoutPutVm.checkoutId())
                .orElseThrow(() -> new NotFoundException(CHECKOUT_NOT_FOUND, checkoutPutVm.checkoutId()));
        checkout.setCheckoutState(CheckoutState.valueOf(checkoutPutVm.checkoutStatus()));
        checkoutRepository.save(checkout);
        Order order = orderService.findOrderByCheckoutId(checkout.getId());
        return order.getId();
    }
}
