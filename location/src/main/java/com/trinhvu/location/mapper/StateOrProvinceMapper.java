package com.trinhvu.location.mapper;

import com.trinhvu.location.model.StateOrProvince;
import com.trinhvu.location.viewmodel.stateorprovince.StateOrProvinceVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StateOrProvinceMapper {

    @Mapping(target = "countryId", source = "country.id")
    StateOrProvinceVm toStateOrProvinceViewModelFromStateOrProvince(StateOrProvince stateOrProvince);
}