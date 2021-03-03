package com.apsol.ecopass.dto.common.juso;

import com.apsol.ecopass.core.juso.gokr.model.Juso;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class JusoDto {

    @NotBlank
    @Length(max=100)
    private String addrJibun;

    @NotBlank
    @Length(max=100)
    private String addrRoad;

    @NotBlank
    @Length(max=100)
    private String addrDetail;

    @NotBlank
    @Length(max=20)
    private String zipCode;

    @NotBlank
    @Length(max=20)
    private String sido;

    @NotBlank
    @Length(max=20)
    private String sgg;

    @NotBlank
    @Length(max=20)
    private String bemd;

    @NotBlank
    @Length(max=20)
    private String hemd;


    private Double lat;


    private Double lng;


}
