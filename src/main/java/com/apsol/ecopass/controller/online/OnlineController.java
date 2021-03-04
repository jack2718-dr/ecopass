package com.apsol.ecopass.controller.online;

import com.apsol.ecopass.dto.common.bulky.BulkyPriceDto;
import com.apsol.ecopass.entity.bulky.BulkyItem;
import com.apsol.ecopass.entity.bulky.QBulkyItem;
import com.apsol.ecopass.service.impl.bulky.BulkyPriceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("online/bulky")
@Slf4j
public class OnlineController {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private BulkyPriceServiceImpl bulkyPriceService;

    @GetMapping(value = "request")
    public String request(Model model) {

        model.addAttribute("req_status", "");

        model.addAttribute("categories", bulkyPriceService.getCategoryNames());

        return "online/bulky/request";
    }

//    @GetMapping(value = "requestTest")
//    private String requestTest(){
//
//        return "online/bulky/request";
//    }


    public List<BulkyItem> findByItemCategoryName(String category){
        QBulkyItem table = QBulkyItem.bulkyItem;
        JPAQuery<BulkyItem> query = queryFactory.selectFrom(table);
        if (!category.equals("전체"))
            query.where(table.bulkyCategory.name.eq(category));
        query.orderBy(table.name.asc());
        return query.fetch();
    }

    @RequestMapping(value = "requestCall")
    public void reqeustCall(HttpServletResponse response,
                            @RequestParam("Sub_group") String Sub_group) throws Exception { // Sub_group : 어떤 카테고리 인지 요청 해당 카테고리에 대한 데이터를 db에서 가져와서 프론트로 다시 보내주는 작업을 해야함
                                                                                            // 현재 서비스에서 리스트 뽑아오는건 구현됨
        log.debug("jsonReqs : {}", Sub_group);

        List<BulkyPriceDto> itemList = bulkyPriceService.getPriceList();
        List<Map<String, Object>> result = new ArrayList<>();
        for(BulkyPriceDto dto : itemList){

        }


    }



}
