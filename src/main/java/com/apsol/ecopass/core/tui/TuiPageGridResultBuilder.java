package com.apsol.ecopass.core.tui;

import com.apsol.ecopass.core.tui.model.TuiPage;
import com.apsol.ecopass.core.tui.model.TuiPageGridData;
import com.apsol.ecopass.core.tui.model.TuiPageGridError;
import com.apsol.ecopass.core.tui.model.TuiPageGridResult;

import java.util.List;

public class TuiPageGridResultBuilder {

    public TuiPageGridResult buildData(int page, int totalCount, List<? extends Object> contents) {
        return new TuiPageGridData(new TuiPage(page, totalCount), contents);
    }

    public TuiPageGridResult buildError(String message) {
        return new TuiPageGridError(message);
    }

}
