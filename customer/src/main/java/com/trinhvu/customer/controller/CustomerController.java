package com.trinhvu.customer.controller;

import com.trinhvu.customer.service.CustomerService;
import com.trinhvu.customer.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerListVm> getCustomers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo
    ) {
        return ResponseEntity.ok(customerService.getCustomers(pageNo));
    }

    @GetMapping("/find-by-email/{email}")
    public ResponseEntity<CustomerAdminVm> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerVm> getCustomerProfile() {
        return ResponseEntity.ok(customerService.getCustomerProfile(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PostMapping("/guest-user")
    public ResponseEntity<GuestVm> createGuestUser() {
        return ResponseEntity.ok(customerService.createGuestUser());
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody CustomerPutVm customerVm) {
        customerService.updateProfile(customerVm);
        return ResponseEntity.noContent().build();
    }
}
