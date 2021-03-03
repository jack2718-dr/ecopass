package com.apsol.ecopass.controller.admin.bulky;

import com.apsol.ecopass.core.tui.TuiPageGridResultBuilder;
import com.apsol.ecopass.core.tui.model.TuiPageGridResult;
import com.apsol.ecopass.dto.admin.bulky.AdminBulkyListDto;
import com.apsol.ecopass.enums.PayGroup;
import com.apsol.ecopass.enums.PayType;
import com.apsol.ecopass.service.BulkyResponseService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


// 2주
@Controller
@RequestMapping(value = "/admin/bulky/list")
public class AdminBulkyListController {

    @Autowired
    BulkyResponseService bulkyResponseService;

    @Autowired
    TuiPageGridResultBuilder tuiPageGridResultBuilder;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @GetMapping
    public String routeRoot(Model model) {


        AdminBulkyListDto searchOptionsModel = new AdminBulkyListDto();

        model.addAttribute("searchOptionsModel", searchOptionsModel);

        return "admin/bulky/list";
    }


    @GetMapping(value = "read")
    @ResponseBody
    public TuiPageGridResult getBulkyOrders(@RequestParam(value = "perPage", defaultValue = "10") int perPage,
                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "orderNo", defaultValue = "") String orderNo,
                                            @RequestParam(value = "name", defaultValue = "") String name,
                                            @RequestParam(value = "phone", defaultValue = "") String phone,
                                            @RequestParam(value = "payGroup", defaultValue = "") PayGroup payGroup,
                                            @RequestParam(value = "payType", defaultValue = "") PayType payType
                                            )
    {
        AdminBulkyListDto searchOptions = new AdminBulkyListDto();
        searchOptions.setOrderNo(orderNo);
        searchOptions.setName(name);
        searchOptions.setPhone(phone);

        List<AdminBulkyListDto> dtos = bulkyResponseService.getOrderListData(perPage, (page - 1) * perPage, searchOptions);
        int totalCount = bulkyResponseService.getOrderListCount(searchOptions);



        return tuiPageGridResultBuilder.buildData(page, totalCount, dtos);
    }



}