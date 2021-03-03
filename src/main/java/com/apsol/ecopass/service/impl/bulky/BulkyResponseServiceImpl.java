package com.apsol.ecopass.service.impl.bulky;

import com.apsol.ecopass.component.EncryptionMapper;
import com.apsol.ecopass.dto.admin.bulky.AdminBulkyListDto;
import com.apsol.ecopass.entity.bulky.QBulkyDetail;
import com.apsol.ecopass.entity.bulky.QBulkyOrder;
import com.apsol.ecopass.enums.PayGroup;
import com.apsol.ecopass.enums.PayType;
import com.apsol.ecopass.service.BulkyResponseService;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BulkyResponseServiceImpl implements BulkyResponseService {

    private static final Logger logger = LoggerFactory.getLogger(BulkyResponseServiceImpl.class);

    private static final QBulkyOrder qBulkyOrder = QBulkyOrder.bulkyOrder;
    private static final QBulkyDetail qBulkyDetail = QBulkyDetail.bulkyDetail;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    EncryptionMapper encryptionMapper;



    public List<AdminBulkyListDto> getOrderListData(int limit, int offset, AdminBulkyListDto searchOption) {
        encryptionMapper.doEncryption(searchOption, true);
        JPAQuery<AdminBulkyListDto> jpaQuery = getOrderListQuery(searchOption);



        logger.info("limit" + limit);

        logger.info("offset" + offset);

        logger.info("searchOption" + searchOption.toString());


        List<AdminBulkyListDto> adminBulkyListDtos = jpaQuery.groupBy(qBulkyOrder.code).limit(limit).offset(offset).fetch();
        for (AdminBulkyListDto adminBulkyListDto : adminBulkyListDtos)
            encryptionMapper.doEncryption(adminBulkyListDto, false);
        return adminBulkyListDtos;
    }


    public int getOrderListCount(AdminBulkyListDto searchOption) {
        JPAQuery<AdminBulkyListDto> jpaQuery = getOrderListQuery(searchOption);
        long totalCount = jpaQuery.groupBy(qBulkyOrder.code).fetchCount();

        logger.info("totalCount" + totalCount);

        return (int) totalCount;
    }

    public JPAQuery<AdminBulkyListDto> getOrderListQuery(AdminBulkyListDto searchOption) {

        JPAQuery<AdminBulkyListDto> jpaQuery = jpaQueryFactory
            .select(Projections.constructor(AdminBulkyListDto.class,
                qBulkyOrder.code,
                qBulkyOrder.empClerkUsername, qBulkyOrder.empCashierUsername,
                qBulkyOrder.districtName, qBulkyOrder.paymentCode, qBulkyOrder.compCleanCode,
                qBulkyOrder.orderNo, qBulkyOrder.payDatetime, qBulkyOrder.scheduleDatetime,
                qBulkyOrder.name, qBulkyOrder.phone, qBulkyOrder.addrJibun,
                qBulkyOrder.addrRoad, qBulkyOrder.addrDetail, qBulkyOrder.position,
                qBulkyOrder.bemd, qBulkyOrder.hemd, qBulkyOrder.payType, qBulkyOrder.freeType,
                qBulkyOrder.canceled, qBulkyOrder.visitPay, qBulkyOrder.visitCollect, qBulkyOrder.smsAgree,
                qBulkyDetail.itemPrice.sum()
            ))
            .from(qBulkyOrder)
            .leftJoin(qBulkyDetail).on(qBulkyOrder.code.eq(qBulkyDetail.bulkyOrder.code))
            .where(
                likeOrderNo(searchOption.getOrderNo()),
                eqName(searchOption.getName()),
                eqPhone(searchOption.getPhone()),
                eqAddrRoad(searchOption.getAddrRoad()),
                eqHemd(searchOption.getHemd()),
                eqCompCleanCode(searchOption.getCompCleanCode()),
                eqPayGroup(searchOption.getPayGroup()),
                eqPayType(searchOption.getPayType()),
                eqVisitPay(searchOption.getVisitPay()),
                eqVisitCollect(searchOption.getVisitCollect()),
                eqCanceled(searchOption.getCanceled()),
                betweenScheduleDate(searchOption.getFrom(), searchOption.getTo())
            );

        return jpaQuery;
    }

    // todo :: 00:00:00부터 59:59:59까지...
    private BooleanExpression betweenScheduleDate(Date from, Date to) {
        if (from == null || to == null)
            return null;
        return qBulkyOrder.scheduleDatetime.between(from, to);
    }

    private BooleanExpression likeOrderNo(String orderNo) {
        if (StringUtils.isBlank(orderNo))
            return null;
        return qBulkyOrder.orderNo.like("%" + orderNo + "%");
    }

    private BooleanExpression eqName(String name) {
        if (StringUtils.isBlank(name))
            return null;
        return qBulkyOrder.name.eq(name);
    }

    private BooleanExpression eqPhone(String phone) {
        if (StringUtils.isBlank(phone))
            return null;
        return qBulkyOrder.phone.eq(phone);
    }

    private BooleanExpression eqAddrRoad(String addrRoad) {
        if (StringUtils.isBlank(addrRoad))
            return null;
        return qBulkyOrder.phone.eq(addrRoad);
    }

    private BooleanExpression eqHemd(String hemd) {
        if (StringUtils.isBlank(hemd))
            return null;
        return qBulkyOrder.hemd.eq(hemd);
    }

    private BooleanExpression eqCompCleanCode(Long compCleanCode) {
        if (null == compCleanCode)
            return null;
        return qBulkyOrder.compCleanCode.eq(compCleanCode);
    }

    private BooleanExpression eqPayGroup(PayGroup payGroup) {
        if (null == payGroup)
            return null;
        return qBulkyOrder.payGroup.eq(Expressions.constant(payGroup));
    }

    private BooleanExpression eqPayType(PayType payType) {
        if (null == payType)
            return null;
        return qBulkyOrder.payType.eq(Expressions.constant(payType));
    }

    private BooleanExpression eqVisitPay(Boolean visitPay) {
        if (null == visitPay)
            return null;
        return qBulkyOrder.visitPay.eq(visitPay);
    }

    private BooleanExpression eqVisitCollect(Boolean visitCollect) {
        if (null == visitCollect)
            return null;
        return qBulkyOrder.visitCollect.eq(visitCollect);
    }

    private BooleanExpression eqCanceled(Boolean canceled) {
        if (null == canceled)
            return null;
        return qBulkyOrder.canceled.eq(canceled);
    }




}
