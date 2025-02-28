package com.trinhvu.customer.controller;

import com.trinhvu.customer.service.CustomerService;
import com.trinhvu.customer.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/backoffice/customers")
    public ResponseEntity<CustomerListVm> getCustomers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo
    ) {
        return ResponseEntity.ok(customerService.getCustomers(pageNo));
    }

    @GetMapping("/backoffice/customers/{email}")
    public ResponseEntity<CustomerAdminVm> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @GetMapping("/backoffice/customers/profile/{id}")
    public ResponseEntity<CustomerVm> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomerProfile(id));
    }

    @PutMapping("/backoffice/customer/profile/{id}")
    public ResponseEntity<Void> updateProfile(@RequestBody CustomerPutVm customerVm, @PathVariable String id) {
        customerService.updateProfile(id, customerVm);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/backoffice/customers/profile/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/storefront/customer/profile")
    public ResponseEntity<CustomerVm> getCustomerProfile() {
        return ResponseEntity.ok(customerService.getCustomerProfile(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PostMapping("storefront/customer/guest-user")
    public ResponseEntity<GuestVm> createGuestUser() {
        return ResponseEntity.ok(customerService.createGuestUser());
    }

}
