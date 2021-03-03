package com.apsol.ecopass.controller.online;

import com.apsol.ecopass.entity.bulky.BulkyItem;
import com.apsol.ecopass.entity.bulky.QBulkyItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
public class OnlineController {

    @Autowired
    private JPAQueryFactory queryFactory;

    @GetMapping(value = "request")
    public String request(Model model) {

        model.addAttribute("req_status", "");

        model.addAttribute("categories", "테스트");

        return "online/bulky/request";
    }


    public List<BulkyItem> findByItemCategoryName(String category){
        QBulkyItem table = QBulkyItem.bulkyItem;
        JPAQuery<BulkyItem> query = queryFactory.selectFrom(table);
        if (!category.equals("전체"))
            query.where(table.bulkyCategory.name.eq(category));
        query.orderBy(table.name.asc());
        return query.fetch();
    }

    @RequestMapping(value = "requestCall.do")
    public void reqeustCall(HttpSession session, HttpServletResponse response,
                            @RequestParam("Sub_group") String Sub_group) throws IOException { // db 건드려야돼서 일단 보류

     //   log.debug("jsonReqs : {}", Sub_group);

        List<BulkyItem> itemList = new ArrayList<>();

        itemList = findByItemCategoryName(Sub_group);

        List<Map<String, Object>> results = new ArrayList<>();
        for (BulkyItem entity : itemList) {
            Map<String, Object> data = new HashMap<>();
            data.put("sub_name", entity.getName());
            data.put("sub_standard", entity.getStandard());
            data.put("sub_price", entity.getPrice());
            results.add(data);
        }

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(results);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }



}
