package com.apsol.ecopass.repository;

import com.apsol.ecopass.entity.area.Addr;
import com.apsol.ecopass.entity.area.AddrId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface AddrRepository extends JpaRepository<Addr, AddrId> {


}
