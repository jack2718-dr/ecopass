package com.apsol.ecopass.dto.common.auth;

import com.apsol.ecopass.entity.auth.AuthItem;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
public class AuthItemDto {

    private AuthDto authDto;

    private RoleDto roleDto;

    public AuthItemDto(@NotNull AuthItem authItem) {
            this.authDto = new AuthDto(authItem.getAuth());
            this.roleDto = new RoleDto(authItem.getRole());
    }

}
