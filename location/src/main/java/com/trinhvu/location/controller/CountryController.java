package com.trinhvu.location.controller;

import com.trinhvu.location.model.Country;
import com.trinhvu.location.service.CountryService;
import com.trinhvu.location.utils.Constants;
import com.trinhvu.location.viewmodel.country.CountryListGetVm;
import com.trinhvu.location.viewmodel.country.CountryPostVm;
import com.trinhvu.location.viewmodel.country.CountryVm;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(Constants.ApiConstant.COUNTRIES_URL)
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/paging")
    public ResponseEntity<CountryListGetVm> getPageableCountries(
            @RequestParam(value = "pageNo", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_NUMBER, required = false)
            final int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_SIZE, required = false)
            final int pageSize) {
        return ResponseEntity.ok(countryService.getPageableCountries(pageNo, pageSize));
    }

    @GetMapping
    public ResponseEntity<List<CountryVm>> listCountries() {
        return ResponseEntity.ok(countryService.findAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryVm> getCountry(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(countryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CountryVm> createCountry(
            @Valid @RequestBody final CountryPostVm countryPostVm,
            final UriComponentsBuilder uriComponentsBuilder) {
        final Country country = countryService.create(countryPostVm);
        return ResponseEntity.created(
                        uriComponentsBuilder
                                .replacePath("/countries/{id}")
                                .buildAndExpand(country.getId())
                                .toUri())
                .body(CountryVm.fromModel(country));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCountry(@PathVariable final Long id,
                                              @Valid @RequestBody final CountryPostVm countryPostVm) {
        countryService.update(countryPostVm, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable(name = "id") final Long id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}