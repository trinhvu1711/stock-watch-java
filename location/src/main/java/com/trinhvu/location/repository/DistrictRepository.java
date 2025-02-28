package com.trinhvu.location.repository;

import java.util.List;

import com.trinhvu.location.model.District;
import com.trinhvu.location.viewmodel.district.DistrictGetVm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<DistrictGetVm> findAllByStateProvinceIdOrderByNameAsc(Long stateProvinceId);
}