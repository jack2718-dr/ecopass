package com.apsol.ecopass.dto.common.area;

import com.apsol.ecopass.entity.area.Addr;
import com.apsol.ecopass.entity.area.District;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class AddrDto {

    private String hemd;

    private String bemd;

    private String districtName;

    public void of(Addr addr) {

        this.bemd = addr.getBemd();
        this.hemd = addr.getHemd();
        this.districtName = addr.getDistrict().getName();

    }

}
