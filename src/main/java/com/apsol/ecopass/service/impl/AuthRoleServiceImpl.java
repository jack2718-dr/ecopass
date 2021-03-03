package com.apsol.ecopass.service.impl;

import com.apsol.ecopass.dto.common.auth.AuthItemDto;
import com.apsol.ecopass.entity.auth.Auth;
import com.apsol.ecopass.entity.auth.AuthItem;
import com.apsol.ecopass.entity.auth.QAuthItem;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.service.AuthRoleService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     *
     * @param authCode          Auth PK
     * @return List<AuthItem>   Auth Item
     */
    @Override
    public List<AuthItemDto> findAuthItemList(Long authCode) {
        if (authCode == null)
            return new ArrayList<>();
        // find by id
        QAuthItem qAuthItem = QAuthItem.authItem;
        List<AuthItem> authItems = jpaQueryFactory.selectFrom(qAuthItem)
            .leftJoin(qAuthItem.auth)
            .leftJoin(qAuthItem.role)
            .fetchJoin()
            .where(qAuthItem.authCode.eq(authCode))
            .where(qAuthItem.auth.deleted.isFalse())
            .where(qAuthItem.role.deleted.isFalse())
            .fetch();

        List<AuthItemDto> authItemDtos = new ArrayList<>();
        for (AuthItem authItem : authItems)
            authItemDtos.add(new AuthItemDto(authItem));

        return authItemDtos;
    }



}
