package com.apsol.ecopass.controller.master;

import com.apsol.ecopass.entity.area.Addr;
import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.repository.AddrRepository;
import com.apsol.ecopass.repository.DistrictRepository;
import com.apsol.ecopass.service.AddrService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/master/addr")
public class MasterAddrController {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    AddrRepository addrRepository;

    @Autowired
    AddrService addrService;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @GetMapping(value = "/update")
    public String routeRoot () {

        return "master/addr/update";
    }

    @PostMapping(value = "update")
    @ResponseBody
    public List<Addr> updateAddrs(@RequestBody List<Addr> addrs) {

        //        if (user.getCompany() == null)
//            throw new RuntimeException("소속 정보를 찾을 수 없습니다.");
//
//        String districtName = user.getCompany().getDistrictName();
//        if (StringUtils.isBlank(districtName))
//            throw new RuntimeException("관할 정보를 찾을 수 없습니다.");

        String districtName = "동대문구";

        Optional<District> districtOptional = districtRepository.findById(districtName);
        if (!districtOptional.isPresent())
            throw new RuntimeException("관할 정보를 찾을 수 없습니다.");

        District district = districtOptional.get();

        addrService.updateAddrs(addrs, district);

        return addrs;
    }

    @PostMapping(value = "/update/excel-upload")
    @ResponseBody
    public List<Addr> uploadExcel(  @RequestParam("file") MultipartFile file  ) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        assert extension != null;
        if (!extension.contains("xlsx") && !extension.equals("xls"))
            throw new RuntimeException("엑셀파일만 업로드 해주세요.");

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

//        if (user.getCompany() == null)
//            throw new RuntimeException("소속 정보를 찾을 수 없습니다.");
//
//        String districtName = user.getCompany().getDistrictName();
//        if (StringUtils.isBlank(districtName))
//            throw new RuntimeException("관할 정보를 찾을 수 없습니다.");

        String districtName = "동대문구";

        Optional<District> districtOptional = districtRepository.findById(districtName);
        if (!districtOptional.isPresent())
            throw new RuntimeException("관할 정보를 찾을 수 없습니다.");

        District district = districtOptional.get();
        List<Addr> addrs = new ArrayList<>();
        // todo :: 역순으로 출력되는 문제
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);

            String hemd = String.valueOf(row.getCell(0));
            String bemd = String.valueOf(row.getCell(1));
            Addr addr = new Addr(hemd, bemd, district);

            addrs.add(addr);
        }

        if (addrs.size() <= 0)
            throw new RuntimeException("빈 엑셀파일입니다.");

        return addrs;
    }

}
