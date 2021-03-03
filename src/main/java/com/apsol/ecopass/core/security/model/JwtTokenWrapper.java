package com.apsol.ecopass.core.security.model;

import com.apsol.ecopass.dto.common.auth.AuthItemDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenWrapper {

    private String token;
    private Date expiresAt;
    private EmployeeDto accessedUser;
    private List<AuthItemDto> authItemDtos;

}
