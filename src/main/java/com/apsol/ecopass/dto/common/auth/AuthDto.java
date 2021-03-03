package com.apsol.ecopass.dto.common.auth;

import com.apsol.ecopass.entity.auth.Auth;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
public class AuthDto {

    private Long code;

    private String name;

    private boolean deleted;

    public AuthDto(@NotNull Auth auth) {
        this.code = auth.getCode();
        this.name = auth.getName();
        this.deleted = auth.isDeleted();
    }

}
