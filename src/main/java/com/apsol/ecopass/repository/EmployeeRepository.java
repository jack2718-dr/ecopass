package com.apsol.ecopass.repository;

import com.apsol.ecopass.entity.member.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {



}
