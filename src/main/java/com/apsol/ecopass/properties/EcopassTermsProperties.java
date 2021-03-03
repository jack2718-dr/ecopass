package com.apsol.ecopass.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ecopass.terms")
@Getter
@Setter
public class EcopassTermsProperties {

    /**
     * 취소 가능일 기준
     */
    private int cancelLimit;

    /**
     * 기간 경과일 기준
     */
    private int overPeriod;

}
