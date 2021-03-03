package com.apsol.ecopass.core.security;

import com.apsol.ecopass.dto.common.auth.AuthItemDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.properties.JwtProperties;
import com.apsol.ecopass.core.security.model.JwtTokenWrapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JwtTokenBuilder {

    public JwtTokenWrapper generateToken (EmployeeDto employeeDto, JwtProperties jwtProperties, List<AuthItemDto> authItemDtos){

        int expirationHours = jwtProperties.getExpirationHours();

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date());               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, expirationHours);      // adds one hour
        Date expiresAt = cal.getTime();

        String jwtToken = JWT.create()
            .withIssuer("apsol")                                // 발급자     iss
            .withSubject(employeeDto.getUsername())             // 제목       sub
            .withAudience(employeeDto.getUsername())            // 대상자     aud
            .withExpiresAt(expiresAt)                           // 만료일시   exp
            .withIssuedAt(new Date())                           // 발급일시   iat
            .sign(Algorithm.HMAC512(jwtProperties.getSecret().getBytes())); // 암호화

        JwtTokenWrapper jwtTokenWrapper = new JwtTokenWrapper();
        jwtTokenWrapper.setToken(jwtToken);
        jwtTokenWrapper.setExpiresAt(expiresAt);
        jwtTokenWrapper.setAccessedUser(employeeDto);
        jwtTokenWrapper.setAuthItemDtos(authItemDtos);

        return jwtTokenWrapper;

    }

}
