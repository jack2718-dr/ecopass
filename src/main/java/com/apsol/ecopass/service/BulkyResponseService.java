package com.apsol.ecopass.service;

import com.apsol.ecopass.dto.admin.bulky.AdminBulkyListDto;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;

public interface BulkyResponseService {

    List<AdminBulkyListDto> getOrderListData(int limit, int offset, AdminBulkyListDto searchOption);

    int getOrderListCount(AdminBulkyListDto searchOption);

    JPAQuery<AdminBulkyListDto> getOrderListQuery(AdminBulkyListDto searchOption);

}
