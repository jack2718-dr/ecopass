package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.dto.common.area.AddrDto;
import com.apsol.ecopass.entity.area.Addr;
import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.entity.area.QAddr;
import com.apsol.ecopass.repository.AddrRepository;
import com.apsol.ecopass.service.AddrService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AddrServiceImpl implements AddrService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    AddrRepository addrRepository;


    @Override
    public void updateAddrs(List<Addr> addrs, District district) {

        QAddr table = QAddr.addr;
        jpaQueryFactory.delete(table).where(table.district.eq(district)).execute();

        addrRepository.saveAll(addrs);
    }

    @Override
    public List<String> getAddrHemds(String jusoBemd, String jusoHemd) {
        List<String> hemds = new ArrayList<>();
        QAddr table = QAddr.addr;
        if (!StringUtils.isBlank(jusoHemd)) {
            hemds = jpaQueryFactory.select(table.hemd)
                    .distinct()
                    .from(table)
                    .where(table.hemd.eq(jusoHemd))
                    .fetch();
            if (hemds.size() != 0) return hemds;
        }
        if (!StringUtils.isBlank(jusoBemd)) {
            hemds = jpaQueryFactory.select(table.hemd)
                    .distinct()
                    .from(table)
                    .where(table.bemd.eq(jusoBemd))
                    .fetch();
        }
        return hemds;
    }

    @Override
    public AddrDto findByEmdsDto(String bemd, String hemd) {
        Addr addr = findByEmds(bemd, hemd);
        AddrDto addrDto = new AddrDto();
        if (addr != null) {
            addrDto.of(addr);
        }
        return addrDto;
    }

    @Override
    public Addr findByEmds(String bemd, String hemd) {
        QAddr table = QAddr.addr;
        Addr addr = jpaQueryFactory.select(table)
            .from(table)
            .where(table.bemd.eq(bemd))
            .where(table.hemd.eq(hemd))
            .fetchOne();

        return addr;
    }

}
