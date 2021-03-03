package com.apsol.ecopass.service;

import com.apsol.ecopass.dto.admin.bulky.AdminBulkyRequestDto;
import com.apsol.ecopass.dto.common.bulky.BulkyDetailDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.dto.online.bulky.OnlineBulkyRequestDto;
import com.apsol.ecopass.entity.bulky.BulkyDetail;
import com.apsol.ecopass.entity.member.Employee;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

public interface BulkyRequestService {

    Long requestAdaptor(OnlineBulkyRequestDto dto) throws ParseException;

    Long requestAdaptor(AdminBulkyRequestDto dto, EmployeeDto employeeDto) throws ParseException;

}
