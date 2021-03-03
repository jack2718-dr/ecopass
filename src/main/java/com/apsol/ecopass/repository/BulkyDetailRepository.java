package com.apsol.ecopass.repository;

import com.apsol.ecopass.entity.bulky.BulkyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkyDetailRepository extends JpaRepository<BulkyDetail, Long> {
}
