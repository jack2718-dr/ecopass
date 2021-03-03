package com.apsol.ecopass.enums;

public enum PayGroup {

    OFFLINE("오프라인"),
    KG_ALLAT("KG 올앳"),
    TOSS_PAY("토스 페이먼츠");

    private String title;

    PayGroup(String title) {
        this.title = title;
    }

    public String getName() {
        return name();
    }

    public String getTitle() {
        return title;
    }
}
