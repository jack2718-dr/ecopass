package com.apsol.ecopass.enums;

import lombok.Getter;

public enum PayType {

    // 오프라인
    OFF_CASH("현금", PayGroup.OFFLINE, true),
    OFF_CARD("카드", PayGroup.OFFLINE, true),
    OFF_BANK("무통장", PayGroup.OFFLINE, false),
    OFF_FREE("수수료면제", PayGroup.OFFLINE, true),
    // 올앳
    CARD("카드(올앳)", PayGroup.KG_ALLAT, false),       // 올앳 : 신청건 생성 및 페이지 이동 후에 결제가 이뤄지기 때문에 즉시결제가 아님
    VBANK("무통장(올앳)", PayGroup.KG_ALLAT, false),
    ABANK("계좌이체(올앳)", PayGroup.KG_ALLAT, false),
    HP("휴대폰(올앳)", PayGroup.KG_ALLAT, false),
    TICKET("상품권(올앳)", PayGroup.KG_ALLAT, false);


    private String title;   // 이름

    @Getter
    private PayGroup payGroup; // 결제방법

    @Getter
    private boolean instantPay;

    PayType(String title, PayGroup payGroup, boolean instantPay) {
        this.title = title;
        this.payGroup = payGroup;
        this.instantPay = instantPay;
    }

    public String getName() {
        return name();
    }

    public String getTitle() {
        return title;
    }


}
