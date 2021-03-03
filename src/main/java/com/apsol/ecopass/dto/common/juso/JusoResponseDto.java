package com.apsol.ecopass.dto.common.juso;

import com.apsol.ecopass.core.juso.gokr.model.Juso;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
public class JusoResponseDto extends JusoDto {

    private List<String> hemdList = new ArrayList<>();

    public void of(@NotBlank Juso juso) {
        this.setAddrJibun(juso.getJibunAddr());
        this.setAddrRoad(juso.getRoadAddrPart1());
        this.setAddrDetail(juso.getRoadAddrPart2());
        this.setZipCode(juso.getZipNo());
        this.setSido(juso.getSiNm());
        this.setSgg(juso.getSggNm());
        this.setBemd(juso.getEmdNm());
        this.setHemd(null);
        if (!StringUtils.isBlank(juso.getEntX())) this.setLat(Double.valueOf(juso.getEntX()));
        if (!StringUtils.isBlank(juso.getEntY())) this.setLng(Double.valueOf(juso.getEntY()));
    }

    public void addHemd(@NotBlank String hemd) {
        this.hemdList.add(hemd);
    }

    public void addHemds(List<String> hemds) {
        this.hemdList.addAll(hemds);
    }

}
