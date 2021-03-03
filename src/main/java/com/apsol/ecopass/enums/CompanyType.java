package com.apsol.ecopass.enums;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum CompanyType {

    ADMIN("개발사", Collections.emptyList()),
    SYSTEM("시스템", Arrays.asList(PayType.CARD, PayType.ABANK, PayType.VBANK)),
    CITY_HALL("시청", Collections.emptyList()),
    DISTRICT_OFFICE("구청", Collections.emptyList()),
    DONG_CENTER("주민센터", Collections.emptyList()),
    CLEAN_COMPANY("수거업체", Arrays.asList(PayType.OFF_CASH, PayType.OFF_BANK)),
    CALL_CENTER("콜센터", Collections.emptyList());

    private String title;

    private List<PayType> payTypeList;

    CompanyType(String title, List<PayType> payTypeList) {
        this.title = title;
        this.payTypeList = payTypeList;
    }

    public String getName() {
        return name();
    }

    public String getTitle() {
        return title;
    }

    public List<PayType> getPayTypeList() { return payTypeList; }

}
