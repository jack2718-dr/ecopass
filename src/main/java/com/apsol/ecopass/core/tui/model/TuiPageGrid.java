package com.apsol.ecopass.core.tui.model;

import lombok.Data;

import java.util.List;

@Data
public class TuiPageGrid {

    private TuiPage pagination;

    private List<? extends Object> contents;

}
