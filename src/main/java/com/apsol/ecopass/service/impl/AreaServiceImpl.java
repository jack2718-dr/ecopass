package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.enums.WasteType;
import com.apsol.ecopass.entity.area.Area;
import com.apsol.ecopass.entity.area.QArea;
import com.apsol.ecopass.service.AreaService;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Area> findByHemdAndWasteType(String hemd, WasteType wasteType) {
        QArea table = QArea.area;
        return jpaQueryFactory.selectFrom(table)
            .leftJoin(table.company).fetchJoin()
            .where(table.hemd.eq(hemd))
            .where(table.wasteType.eq(wasteType))
            .fetch();
    }

}
