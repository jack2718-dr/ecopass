package com.apsol.ecopass.controller.common;

import com.apsol.ecopass.component.EncryptionMapper;
import com.apsol.ecopass.core.tui.model.TuiPageGridResult;
import com.apsol.ecopass.core.tui.TuiPageGridResultBuilder;
import com.apsol.ecopass.dto.common.bulky.BulkyPriceDto;
import com.apsol.ecopass.entity.bulky.BulkyItem;
import com.apsol.ecopass.service.BulkyPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/common/bulky-price")
public class BulkyPriceController {

    private static final Logger logger = LoggerFactory.getLogger(BulkyPriceController.class);

    @Autowired
    BulkyPriceService bulkyPriceService;

    @Autowired
    TuiPageGridResultBuilder tuiPageGridResultBuilder;

    @Autowired
    EncryptionMapper encryptionMapper;

    @GetMapping(value = "list")
    @ResponseBody
    public TuiPageGridResult getPriceList() {

        List<BulkyPriceDto> bulkyPriceDtoList = bulkyPriceService.getPriceList();

        return tuiPageGridResultBuilder.buildData(1, bulkyPriceDtoList.size(), bulkyPriceDtoList);
    }

}
