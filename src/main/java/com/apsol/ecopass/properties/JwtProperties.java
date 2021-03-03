package com.apsol.ecopass.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    /**
     * 토큰 password
     */
    public String secret;

    /**
     * 만료 기간
     */
    public int expirationHours;

    /**
     * 토큰 공통 식별자
     */
    public String tokenPrefix;

    /**
     * 토큰 유형
     */
    public String headerString;

}
