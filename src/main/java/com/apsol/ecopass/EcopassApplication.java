package com.apsol.ecopass;

import com.apsol.ecopass.controller.admin.bulky.AdminBulkyRequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class EcopassApplication {

    private static final Logger logger = LoggerFactory.getLogger(EcopassApplication.class);

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        logger.info("현재 시각 :: " + new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(EcopassApplication.class, args);
    }

}
