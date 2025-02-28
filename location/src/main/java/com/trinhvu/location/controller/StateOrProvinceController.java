package com.trinhvu.location.controller;

import com.trinhvu.location.model.StateOrProvince;
import com.trinhvu.location.service.StateOrProvinceService;
import com.trinhvu.location.utils.Constants;
import com.trinhvu.location.viewmodel.stateorprovince.StateOrProvinceAndCountryGetNameVm;
import com.trinhvu.location.viewmodel.stateorprovince.StateOrProvinceListGetVm;
import com.trinhvu.location.viewmodel.stateorprovince.StateOrProvincePostVm;
import com.trinhvu.location.viewmodel.stateorprovince.StateOrProvinceVm;
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
@RequestMapping(Constants.ApiConstant.STATE_OR_PROVINCES_URL)
public class StateOrProvinceController {

    private final StateOrProvinceService stateOrProvinceService;

    public StateOrProvinceController(StateOrProvinceService stateOrProvinceService) {
        this.stateOrProvinceService = stateOrProvinceService;
    }

    /**
     * API  paging list of state or province by country id.
     *
     * @param pageNo    The number of page
     * @param pageSize  The number of row on every page
     * @param countryId The country Id which  state or province belong
     * @return StateOrProvinceListGetVm   The list of StateOrProvince
     */
    @GetMapping("/paging")
    public ResponseEntity<StateOrProvinceListGetVm> getPageableStateOrProvinces(
            @RequestParam(value = "pageNo", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_NUMBER, required = false)
            final int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.PageableConstant.DEFAULT_PAGE_SIZE, required = false)
            final int pageSize,
            @RequestParam(value = "countryId", required = false) final Long countryId) {
        return ResponseEntity.ok(
                stateOrProvinceService.getPageableStateOrProvinces(pageNo, pageSize, countryId));
    }

    @GetMapping
    public ResponseEntity<List<StateOrProvinceVm>> getAllByCountryId(
            @RequestParam(value = "countryId", required = false) final Long countryId) {
        return ResponseEntity.ok(
                stateOrProvinceService.getAllByCountryId(countryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateOrProvinceVm> getStateOrProvince(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(stateOrProvinceService.findById(id));
    }

    /**
     * API Get list names for state and country by list of state or province ids.
     *
     * @param stateOrProvinceIds The list of state or province ids
     * @return StateOrProvinceAndCountryGetNameVm   The list of state and country names
     */
    @GetMapping("state-country-names")
    public ResponseEntity<List<StateOrProvinceAndCountryGetNameVm>> getStateOrProvinceAndCountryNames(
            @RequestParam(value = "stateOrProvinceIds") final List<Long> stateOrProvinceIds) {
        return ResponseEntity.ok(stateOrProvinceService.getStateOrProvinceAndCountryNames(stateOrProvinceIds));
    }

    /**
     * API create state or province.
     *
     * @param stateOrProvincePostVm The state or province post Dto
     * @return StateOrProvince
     */
    @PostMapping
    public ResponseEntity<StateOrProvinceVm> createStateOrProvince(
            @Valid @RequestBody final StateOrProvincePostVm stateOrProvincePostVm,
            final UriComponentsBuilder uriComponentsBuilder) {
        final StateOrProvince stateOrProvince = stateOrProvinceService.createStateOrProvince(
                stateOrProvincePostVm);
        return ResponseEntity.created(uriComponentsBuilder.replacePath("/state-or-provinces/{id}")
                        .buildAndExpand(stateOrProvince.getId()).toUri())
                .body(StateOrProvinceVm.fromModel(stateOrProvince));
    }

    /**
     * API update state or province.
     *
     * @param stateOrProvincePostVm The state or province post Dto
     * @param id                    The id of stateOrProvince need to update
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStateOrProvince(@PathVariable final Long id,
                                                      @Valid @RequestBody
                                                      final StateOrProvincePostVm stateOrProvincePostVm) {
        stateOrProvinceService.updateStateOrProvince(stateOrProvincePostVm, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStateOrProvince(@PathVariable final long id) {
        stateOrProvinceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}