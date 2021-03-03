package com.apsol.ecopass.enums;


public enum BulkyState {

    STANDBY("수거대기"),
    READY_COMPLETE("완료대기"),
    COMPLETED("수거완료"),
    READY_REJECT("거부대기"),
    REJECTED("수거거부"),
    READY_CANCEL("취소대기"),
    CANCELED("배출취소"),
    NON_EXHAUSTED("미배출"),
    OVER_PERIOD("기간경과"),
    READY_DEPOSIT("입금대기");

    private String title;

    BulkyState(String title) {
        this.title = title;
    }

    public String getName() {
        return name();
    }

    public String getTitle() {
        return title;
    }
}
