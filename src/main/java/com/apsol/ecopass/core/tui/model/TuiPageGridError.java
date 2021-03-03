package com.apsol.ecopass.core.tui.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
public class TuiPageGridError implements TuiPageGridResult {

    private final boolean result = false;

    @Getter
    private String message = "";

    public TuiPageGridError(String message) {
        this.message = message;
    }

    @Override
    public boolean isResult() {
        return result;
    }

}
