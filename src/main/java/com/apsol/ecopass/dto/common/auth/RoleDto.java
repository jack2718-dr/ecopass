package com.apsol.ecopass.dto.common.auth;

import com.apsol.ecopass.entity.auth.Role;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
public class RoleDto {

    private String name;

    private String memo;

    private boolean deleted;

    public RoleDto(@NotNull Role role) {
        this.name = role.getName();
        this.memo = role.getMemo();
        this.deleted = role.isDeleted();
    }

}
