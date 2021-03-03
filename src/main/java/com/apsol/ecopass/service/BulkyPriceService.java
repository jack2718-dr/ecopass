package com.apsol.ecopass.service;

import com.apsol.ecopass.dto.common.bulky.BulkyPriceDto;
import com.apsol.ecopass.entity.bulky.BulkyItem;

import java.util.List;

public interface BulkyPriceService {

    List<BulkyPriceDto> getPriceList();
    List<String> getCategoryNames();


}
