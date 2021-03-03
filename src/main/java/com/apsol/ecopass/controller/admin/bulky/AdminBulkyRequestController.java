package com.apsol.ecopass.controller.admin.bulky;

import com.apsol.ecopass.dto.admin.bulky.AdminBulkyRequestDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.service.BulkyPriceService;
import com.apsol.ecopass.service.BulkyRequestService;
import com.apsol.ecopass.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/bulky/request")
public class AdminBulkyRequestController {

    private static final Logger logger = LoggerFactory.getLogger(AdminBulkyRequestController.class);

    @Autowired
    BulkyRequestService bulkyRequestService;

    @Autowired
    BulkyPriceService bulkyPriceService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public String routeRequestPage( Model model,
                                    AdminBulkyRequestDto adminBulkyRequestDto,
                                    @AuthenticationPrincipal EmployeeDto employeeDto ) {

        List<String> categoryNames = bulkyPriceService.getCategoryNames();
        categoryNames.add(0, "전체");

        model.addAttribute("categoryNames", categoryNames);

        return "admin/bulky/request";
    }

    @PostMapping()
    public String processRequest(   @Valid AdminBulkyRequestDto adminBulkyRequestDto, BindingResult bindingResult,
                                    @AuthenticationPrincipal EmployeeDto employeeDto ) throws ParseException {
        logger.info(adminBulkyRequestDto.toString());

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors)
                logger.info(fieldError.toString());
            return "admin/bulky/request";
        }

        long orderCode = bulkyRequestService.requestAdaptor(adminBulkyRequestDto, employeeDto);

        return "redirect:/admin/bulky/request/result/" + orderCode;
    }

    @GetMapping(value = "/result/{orderNo}")
    public String routeResultPage(@PathVariable String orderNo) {

        return "admin/bulky/request-result";
    }



}
