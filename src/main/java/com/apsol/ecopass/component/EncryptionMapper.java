package com.apsol.ecopass.component;

import com.apsol.ecopass.annotations.TwoWayEncryption;
import com.apsol.ecopass.controller.MainController;
import com.apsol.ecopass.util.SuperClassReflectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class EncryptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    TextEncryptor textEncryptor;

    public void doEncryption(Object object, boolean isEncrypt) {
        // 객체가 null인 경우
        if (object == null)
            return;
        // 필드 목록을 가져온 후 루프
        for (Field field : SuperClassReflectionUtils.getAllFields(object.getClass())) {
            // TwoWayEncryption 어노테이션을 가진 필드를 검색
            if (field.isAnnotationPresent(TwoWayEncryption.class)) {
                // 접근을 허용시킨다
                field.setAccessible(true);
                // 문자열이 아닐 경우 무시한다
                Object type = field.getType();
                if (!type.equals(String.class))
                    continue;
                // 값을 추출
                try {
                    Object value = field.get(object);
                    // "Empty"일 경우 "null" 입력하고 건너뜀
                    if(ObjectUtils.isEmpty(value)) {
                        field.set(object, null);
                        continue;
                    }
                    // 암|복호화
                    if (isEncrypt)
                        field.set(object, textEncryptor.encrypt((String) value));
                    else
                        field.set(object, textEncryptor.decrypt((String) value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
