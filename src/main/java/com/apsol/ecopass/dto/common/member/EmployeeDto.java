package com.apsol.ecopass.dto.common.member;

import com.apsol.ecopass.dto.common.auth.AuthDto;
import com.apsol.ecopass.entity.auth.Auth;
import com.apsol.ecopass.entity.member.Company;
import com.apsol.ecopass.entity.member.Employee;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@ToString
public class EmployeeDto {

    private String username;
    private String name;
    private String phone;
    private String email;
    private boolean master;
    private boolean deleted;

    private Long companyCode;
    private Long authCode;

    public EmployeeDto(Employee employee) {
        this.username = employee.getUsername();
        this.name = employee.getName();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.master = employee.isMaster();
        this.deleted = employee.isDeleted();

        this.companyCode = employee.getCompanyCode();
        this.authCode = employee.getAuthCode();
    }

}
