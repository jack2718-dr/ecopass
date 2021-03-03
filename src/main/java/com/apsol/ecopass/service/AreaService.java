package com.apsol.ecopass.service;

import com.apsol.ecopass.enums.WasteType;
import com.apsol.ecopass.entity.area.Area;

import java.util.List;

public interface AreaService {

    List<Area> findByHemdAndWasteType(String hemd, WasteType wasteType);

}
