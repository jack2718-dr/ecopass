package com.apsol.ecopass.entity.area;

import com.apsol.ecopass.enums.WasteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaId implements Serializable {

    private static final long serialVersionUID = 5266851602526896493L;

    private String hemd;

    private WasteType wasteType;

    private Long companyCode;

}
