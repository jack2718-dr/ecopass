package com.apsol.ecopass.core.tui.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
public class TuiPageGridData implements TuiPageGridResult {

    private final boolean result = true;

    @Getter
    private TuiPageGrid data = new TuiPageGrid();

    public TuiPageGridData(TuiPage tuiPage, List<? extends Object> contents) {
        this.data.setPagination(tuiPage);
        this.data.setContents(contents);
    }

    @Override
    public boolean isResult() {
        return result;
    }

}
