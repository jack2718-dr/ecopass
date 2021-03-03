package com.apsol.ecopass.service.impl.bulky;

import com.apsol.ecopass.dto.common.bulky.BulkyPriceDto;
import com.apsol.ecopass.entity.bulky.BulkyItem;
import com.apsol.ecopass.entity.bulky.QBulkyCategory;
import com.apsol.ecopass.entity.bulky.QBulkyItem;
import com.apsol.ecopass.service.BulkyPriceService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BulkyPriceServiceImpl implements BulkyPriceService {

    private static final Logger logger = LoggerFactory.getLogger(BulkyPriceServiceImpl.class);

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    public List<BulkyPriceDto> getPriceList() {
        QBulkyItem table = QBulkyItem.bulkyItem;
        List<BulkyItem> bulkyItemList = jpaQueryFactory
                .selectFrom(table)
                .leftJoin(table.bulkyCategory)
                .fetchJoin()
                .where(table.deleted.isFalse())
                .where(table.bulkyCategory.deleted.isFalse())
                .fetch();

        List<BulkyPriceDto> bulkyPriceDtoList = new ArrayList<>();

        for (BulkyItem bulkyItem : bulkyItemList) {
            BulkyPriceDto bulkyPriceDto = new BulkyPriceDto();
            bulkyPriceDto.of(bulkyItem);
            bulkyPriceDtoList.add(bulkyPriceDto);
        }

        return bulkyPriceDtoList;
    }

    public List<String> getCategoryNames() {
        QBulkyCategory table = QBulkyCategory.bulkyCategory;

        return jpaQueryFactory.select(table.name)
            .from(table)
            .where(table.deleted.isFalse())
            .fetch();
    }

}
