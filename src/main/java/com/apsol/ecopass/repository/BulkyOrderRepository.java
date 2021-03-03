package com.apsol.ecopass.repository;

import com.apsol.ecopass.entity.bulky.BulkyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulkyOrderRepository extends JpaRepository<BulkyOrder, Long> {
}
