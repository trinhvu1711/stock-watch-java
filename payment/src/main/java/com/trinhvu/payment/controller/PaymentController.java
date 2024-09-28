package com.trinhvu.payment.controller;

import com.trinhvu.payment.service.PaymentService;
import com.trinhvu.payment.viewmodel.CapturePayment;
import com.trinhvu.payment.viewmodel.PaymentOrderStatusVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/capture")
    public ResponseEntity<PaymentOrderStatusVm> capturePayment(@Valid @RequestBody CapturePayment capturePayment) {
        return ResponseEntity.ok(paymentService.capturePayment(capturePayment));
    }

}
