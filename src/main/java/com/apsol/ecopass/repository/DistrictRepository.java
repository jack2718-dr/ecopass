package com.apsol.ecopass.repository;

import com.apsol.ecopass.entity.area.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, String> {

}
