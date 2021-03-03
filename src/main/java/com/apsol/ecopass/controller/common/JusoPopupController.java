package com.apsol.ecopass.controller.common;

import com.apsol.ecopass.core.juso.gokr.GokrJusoBuilder;
import com.apsol.ecopass.core.juso.gokr.model.Juso;
import com.apsol.ecopass.core.juso.gokr.model.JusoCommon;
import com.apsol.ecopass.core.juso.gokr.model.JusoResults;
import com.apsol.ecopass.properties.JusoApiProperties;
import com.apsol.ecopass.core.tui.model.TuiPageGridResult;
import com.apsol.ecopass.core.tui.TuiPageGridResultBuilder;
import com.apsol.ecopass.dto.common.juso.JusoResponseDto;
import com.apsol.ecopass.service.AddrService;
import com.apsol.ecopass.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping(value = "/common/juso-popup")
public class JusoPopupController {

    private static final Logger logger = LoggerFactory.getLogger(JusoPopupController.class);

    @Autowired
    JusoApiProperties jusoApiProperties;

    @Autowired
    TuiPageGridResultBuilder tuiPageGridResultBuilder;

    @Autowired
    AreaService areaService;

    @Autowired
    AddrService addrService;

    @GetMapping(value = "/page")
    public String routePage() {

        return "common/juso-popup";
    }

    /**
     * 주소를 검색하고 tui-grid api 형태로 데이터를 반환한다.
     * @param perPage   페이지당 개수
     * @param page      현재 페이지
     * @param keyword   검색어
     * @return          그리드 데이터
     */
    @GetMapping(value = "/search")
    @ResponseBody
    public TuiPageGridResult searchJuso(    @RequestParam(value = "perPage", defaultValue = "5") int perPage,
                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "keyword") String keyword     ) {
        JusoResults jusoResults;
        List<Juso> jusoList;
        JusoCommon jusoCommon;
        try {
            // 주소 검색 요청
            jusoResults = new GokrJusoBuilder().getJuso(keyword, page, perPage, jusoApiProperties);
            jusoCommon = jusoResults.getResults().getCommon();
            jusoList = jusoResults.getResults().getJuso();

            // 에러코드 반환
            if (!jusoCommon.getErrorCode().equals("0")) {
                return tuiPageGridResultBuilder.buildError(jusoCommon.getErrorCode() + " :: " + jusoCommon.getErrorMessage());
            }
        } catch (Exception e) {
            // NPE 등 예외처리
            return tuiPageGridResultBuilder.buildError("시스템 에러");
        }
        // 데이터 간결화
        for (Juso juso : jusoList) {
            juso.setJibunAddr(juso.getJibunAddr().replaceFirst(juso.getSiNm(), ""));
            juso.setJibunAddr(juso.getJibunAddr().replaceFirst(" ", ""));
            juso.setRoadAddrPart1(juso.getRoadAddrPart1().replaceFirst(juso.getSiNm(), ""));
            juso.setRoadAddrPart1(juso.getRoadAddrPart1().replaceFirst(" ", ""));
        }
        return tuiPageGridResultBuilder.buildData(jusoCommon.getCurrentPage(), jusoCommon.getTotalCount(), jusoList);
    }

    @PostMapping(value = "/location-hemd")
    @ResponseBody
    public JusoResponseDto searchPosition(@RequestBody Juso juso) {

        JusoResponseDto responseDto = new JusoResponseDto();
        try {
            // 좌표 검색 요청
            JusoResults result = new GokrJusoBuilder().getPosition(juso, jusoApiProperties);
            if (result.getResults().getCommon().getTotalCount() > 0) {
                // 원본 데이터에 좌표 데이터 세팅
                Juso resJuso = result.getResults().getJuso().get(0);
                juso.setEntX(resJuso.getEntX());
                juso.setEntY(resJuso.getEntY());

            } else {
                return null; // 데이터가 없는 경우
            }
        } catch (URISyntaxException | IOException e) {
            return null; // NPE 등 예외처리
        }
        // 행정동 추출
        String hemdStr = juso.getHemdNm();
        if (hemdStr != null) {
            hemdStr = hemdStr.replaceFirst(juso.getSiNm(), "");
            hemdStr = hemdStr.replaceFirst(juso.getSggNm(), "");
            hemdStr = hemdStr.replaceAll(" ", "");
        }
        // 법정동-행정동 매칭 데이터 검색
        List<String> hemds = addrService.getAddrHemds(juso.getEmdNm(), hemdStr);
        // DTO로 변환
        responseDto.of(juso);
        responseDto.addHemds(hemds);

        return responseDto;
    }

}





















































