package com.apsol.ecopass.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminRootController {

    private static final Logger logger = LoggerFactory.getLogger(AdminRootController.class);

    @GetMapping()
    public String routeRoot() {
        return "redirect:/admin/main";
    }

}
