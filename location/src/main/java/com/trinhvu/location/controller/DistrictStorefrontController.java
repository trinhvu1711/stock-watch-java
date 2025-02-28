package com.trinhvu.location.controller;

import com.trinhvu.location.service.DistrictService;
import com.trinhvu.location.viewmodel.district.DistrictGetVm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DistrictStorefrontController {
    private final DistrictService districtService;

    @GetMapping({"/storefront/district/{id}", "/backoffice/district/{id}"})
    public ResponseEntity<List<DistrictGetVm>> getList(@PathVariable Long id) {
        return ResponseEntity.ok(districtService.getList(id));
    }
}