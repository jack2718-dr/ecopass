package com.apsol.ecopass.dto.common.bulky;

import com.apsol.ecopass.dto.common.juso.JusoDto;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class BulkyRequestDto extends JusoDto {

    @NotBlank
    @Length(min=2, max=30)
    private String name;

    @NotBlank
    @Length(min=4, max=30)
    private String phone;

    @NotBlank
    @Length(max=50)
    private String position;


    private boolean smsAgree = true;

    @NotBlank
    private String scheduleDate;

    @NotBlank
    private String scheduleTime;


    private BigDecimal totalAmount = BigDecimal.ZERO;


    private List<BulkyPriceDto> priceDtos = new ArrayList<>();




//    /**
//     * 인증번호
//     * 입력값
//     */
//    @NotBlank
//    @Length(max=6)
//    private String authNumber;
//
//    /**
//     * 토큰번호
//     * Hidden
//     */
//    @NotBlank
//    @Length(max=6)
//    private String tokenNumber;
//
//    /**
//     * 인증여부
//     * Hidden
//     */
//    @NotBlank
//    private boolean phoneAuthCheck = false;

}
