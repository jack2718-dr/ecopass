package com.apsol.ecopass.controller.api;

import com.apsol.ecopass.dto.common.auth.AuthItemDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.properties.JwtProperties;
import com.apsol.ecopass.core.security.*;
import com.apsol.ecopass.core.security.model.JwtTokenWrapper;
import com.apsol.ecopass.core.security.model.LoginRequest;
import com.apsol.ecopass.service.AuthRoleService;
import com.apsol.ecopass.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class ApiAuthController extends AbstractApiController {

    @Autowired
    JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AuthRoleService authRoleService;

    @RequestMapping(value = "authorize", method = { RequestMethod.POST })
    @ResponseBody
    public JwtTokenWrapper authorize(HttpServletResponse response, @RequestBody LoginRequest loginRequest) throws AuthenticationException {
        // 응답 객체에 인증 헤더 세팅
        setHeader(response, "POST", "text");
        // 로그인 시도하여 DTO 생성
        EmployeeDto employeeDto = employeeService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        // 권한 DTO 쿼리
        List<AuthItemDto> authItemDtos = new ArrayList<>();
        if (employeeDto.getAuthCode() != null)
            authItemDtos = authRoleService.findAuthItemList(employeeDto.getAuthCode());
        // 토큰 생성하여 반환
        return jwtTokenBuilder.generateToken(employeeDto, jwtProperties, authItemDtos);
    }

    // todo :: refreash 구현할 것

}
