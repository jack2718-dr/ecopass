package com.apsol.ecopass.service;

import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.entity.member.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface EmployeeService {

    EmployeeDto authenticate(String username) throws AuthenticationException;

    EmployeeDto authenticate(String username, String password) throws AuthenticationException;

    EmployeeDto authenticate(Authentication authentication) throws AuthenticationException;

    Employee findByUsername(String username) throws AuthenticationException;

}
