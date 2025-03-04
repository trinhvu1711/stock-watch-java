package com.trinhvu.location.controller;

import com.trinhvu.location.service.AddressService;
import com.trinhvu.location.viewmodel.address.AddressDetailVm;
import com.trinhvu.location.viewmodel.address.AddressGetVm;
import com.trinhvu.location.viewmodel.address.AddressPostVm;
import jakarta.validation.Valid;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController {
    @Autowired
    private AddressService addressService;


    @PostMapping("/storefront/addresses")
    public ResponseEntity<AddressGetVm> createAddress(@Valid @RequestBody AddressPostVm dto) {
        return ResponseEntity.ok(addressService.createAddress(dto));
    }

    @PutMapping("/storefront/addresses/{id}")
    public ResponseEntity<Void> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressPostVm dto) {
        addressService.updateAddress(id, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/storefront/addresses/{id}")
    public ResponseEntity<AddressDetailVm> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @GetMapping("/storefront/addresses")
    public ResponseEntity<List<AddressDetailVm>> getAddressList(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(addressService.getAddressList(ids));
    }

    @DeleteMapping("/storefront/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }
}