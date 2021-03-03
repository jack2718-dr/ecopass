package com.apsol.ecopass.dto.common.member;

import com.apsol.ecopass.dto.common.area.DistrictDto;
import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.enums.CompanyType;
import com.apsol.ecopass.entity.member.Company;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CompanyDto {

    private long code;
    private CompanyType type;
    private String name;
    private String phone;
    private boolean deleted;

    private String districtName;

    public CompanyDto(Company company) {
        this.code = company.getCode();
        this.type = company.getType();
        this.name = company.getName();
        this.phone = company.getPhone();
        this.deleted = company.isDeleted();

        this.districtName = company.getDistrictName();
    }

}
