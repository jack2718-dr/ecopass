package com.apsol.ecopass.config;

import com.apsol.ecopass.core.security.JwtTokenBuilder;
import com.apsol.ecopass.core.tui.TuiPageGridResultBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

    @Bean
    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        /*연결 전략 : 같은 타입의 필드명이 같은 경우만 동작*/
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    @Bean
    public static TuiPageGridResultBuilder getTuiPageGridResultBuilder() {
        return new TuiPageGridResultBuilder();
    }

    @Bean
    public static JwtTokenBuilder getJwtTokenBuilder() {
        return new JwtTokenBuilder();
    }



}
