package com.apsol.ecopass.service;

import com.apsol.ecopass.dto.common.auth.AuthItemDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.entity.auth.Auth;
import com.apsol.ecopass.entity.auth.AuthItem;
import com.apsol.ecopass.entity.member.Employee;

import java.util.List;

public interface AuthRoleService {

    List<AuthItemDto> findAuthItemList(Long authCode);

}
