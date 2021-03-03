package com.apsol.ecopass.enums;


public enum FreeType {

    SUBSIDY("수급자"),
    SINGLE("한부모가정"),
    NATIONAL("국가유공자");

    private String title;

    FreeType(String title) {
        this.title = title;
    }

    public String getName() {
        return name();
    }

    public String getTitle() {
        return title;
    }
}
