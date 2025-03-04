package com.trinhvu.location.service;

import com.trinhvu.location.repository.DistrictRepository;
import com.trinhvu.location.viewmodel.district.DistrictGetVm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public List<DistrictGetVm> getList(Long id) {
        return districtRepository.findAllByStateProvinceIdOrderByNameAsc(id);
    }
}