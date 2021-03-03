package com.apsol.ecopass.component;

import com.apsol.ecopass.dto.common.auth.AuthItemDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.service.AuthRoleService;
import com.apsol.ecopass.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 로그인 요청시 처리
 */
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthRoleService authRoleService;

    @Override
    public Authentication authenticate(Authentication authentication)  {
        // auth user
        EmployeeDto employeeDto = employeeService.authenticate(authentication);
        // query role
        List<AuthItemDto> authItemDtos = new ArrayList<>();
        if (employeeDto.getAuthCode() != null)
            authItemDtos = authRoleService.findAuthItemList(employeeDto.getAuthCode());
        // register roles
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (AuthItemDto authItemDto : authItemDtos) {
            authorities.add(new SimpleGrantedAuthority(authItemDto.getRoleDto().getName()));
        }
        // register a master role
        if(employeeDto.isMaster()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MASTER"));
        }
        // success
        return new UsernamePasswordAuthenticationToken(employeeDto, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // warn : the return value must always be 'true'
        return true;
    }

}



















































