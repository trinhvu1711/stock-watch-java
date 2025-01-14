package com.trinhvu.location.viewmodel.stateorprovince;

import com.trinhvu.location.model.StateOrProvince;

public record StateOrProvinceVm(Long id, String name, String code, String type, Long countryId) {

    public static StateOrProvinceVm fromModel(StateOrProvince stateOrProvince) {
        return new StateOrProvinceVm(stateOrProvince.getId(), stateOrProvince.getName(),
                stateOrProvince.getCode(),
                stateOrProvince.getType(), stateOrProvince.getCountry().getId());
    }
}