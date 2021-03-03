package com.apsol.ecopass.controller;

import com.apsol.ecopass.controller.admin.bulky.AdminBulkyRequestController;
import com.apsol.ecopass.entity.member.QEmployee;
import com.apsol.ecopass.repository.DistrictRepository;
import com.apsol.ecopass.repository.EmployeeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TextEncryptor textEncryptor;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DistrictRepository districtRepository;

    @GetMapping()
    public String routeRoot() {



        return "main";
    }

    @GetMapping(value = "/test/annotation")
    public String testAnnotation() {



//        BulkyOrder bulkyOrder = BulkyOrder.builder().build();
//
//        List<Field> cryptoFields = new ArrayList<>();
//
//        for (Field field : SuperClassReflectionUtils.getAllFields(bulkyOrder.getClass())) {
//            if (field.isAnnotationPresent(Crypto.class)) {
//                cryptoFields.add(field);
//
//                System.out.println(field.getName());
//
//                /**/
//                CryptoType cryptoType = field.getAnnotation(Crypto.class).cryptoType();
//                if (cryptoType == CryptoType.TWO_WAY)
//                    System.out.println(cryptoType);
//            }
//        }
//        System.out.println(cryptoFields.toString());

        return "index";
    }

    @GetMapping(value = "/test/crypto")
    public String testCrypto() {

        String enc = textEncryptor.encrypt("ㅎㅇ");
        String enc3 = textEncryptor.encrypt("ㅎㅇ");
        System.out.println(enc);
        System.out.println(enc3);

        return "index";
    }

    @GetMapping(value = "/test/mk-emp")
    public String makeEmployee() {

        return "index";
    }

    @GetMapping(value = "/test/juso")
    public String testJuso() {



        return "index";
    }

}


