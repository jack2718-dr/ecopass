package com.apsol.ecopass.dto.common.area;

import com.apsol.ecopass.entity.area.District;

public class DistrictDto {

    private String name;

    public DistrictDto(District district) {
        this.name = district.getName();
    }

}
