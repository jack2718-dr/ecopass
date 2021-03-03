package com.apsol.ecopass.controller.admin;

import com.apsol.ecopass.dto.admin.bulky.AdminBulkyListDto;
import com.apsol.ecopass.service.BulkyResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/main")
public class AdminMainController {

    @Autowired
    BulkyResponseService bulkyResponseService;

    private static final Logger logger = LoggerFactory.getLogger(AdminMainController.class);

    @GetMapping
    public String routeRoot(Model model) {


        AdminBulkyListDto searchOptions = new AdminBulkyListDto();


        model.addAttribute("searchOptions", searchOptions);

        return "admin/main";
    }

}
