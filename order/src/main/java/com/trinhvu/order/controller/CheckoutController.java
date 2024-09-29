package com.trinhvu.order.controller;

import com.trinhvu.order.service.CheckoutService;
import com.trinhvu.order.viewmodel.checkout.CheckoutPostVm;
import com.trinhvu.order.viewmodel.checkout.CheckoutPutVm;
import com.trinhvu.order.viewmodel.checkout.CheckoutVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/checkouts")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutVm> createCheckOut(CheckoutPostVm checkoutPostVm) {
        return ResponseEntity.ok(checkoutService.createCheckOut(checkoutPostVm));
    }

    @PutMapping("/status")
    public ResponseEntity<Long> updateCheckoutStatus(CheckoutPutVm checkoutPutVm) {
        return ResponseEntity.ok(checkoutService.updateCheckoutStatus(checkoutPutVm));
    }
}
