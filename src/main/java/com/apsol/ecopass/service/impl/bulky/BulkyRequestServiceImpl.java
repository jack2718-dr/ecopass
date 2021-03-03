package com.apsol.ecopass.service.impl.bulky;

import com.apsol.ecopass.component.EncryptionMapper;
import com.apsol.ecopass.dto.common.bulky.BulkyDetailDto;
import com.apsol.ecopass.dto.common.member.EmployeeDto;
import com.apsol.ecopass.enums.*;
import com.apsol.ecopass.dto.admin.bulky.AdminBulkyRequestDto;
import com.apsol.ecopass.dto.common.bulky.BulkyPriceDto;
import com.apsol.ecopass.dto.common.bulky.BulkyRequestDto;
import com.apsol.ecopass.dto.online.bulky.OnlineBulkyRequestDto;
import com.apsol.ecopass.entity.area.Addr;
import com.apsol.ecopass.entity.area.Area;
import com.apsol.ecopass.entity.bulky.*;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.repository.BulkyDetailRepository;
import com.apsol.ecopass.repository.BulkyOrderRepository;
import com.apsol.ecopass.service.AddrService;
import com.apsol.ecopass.service.AreaService;
import com.apsol.ecopass.service.BulkyRequestService;
import com.apsol.ecopass.service.EmployeeService;
import com.apsol.ecopass.util.DateFormatHelper;
import com.apsol.ecopass.util.HolidayUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 작성자 : 이상헌
 *
 * 1. 발생 메서드   (generate-)
 *      - 생성자 혹은 Builder 패턴을 통해 엔티티 데이터가 최초로 생성되는 지점이다.
 * 2. 초기화 메서드 (init-)
 *      - 엔티티 데이터를 초기화할 때 사용하는 메서드이다.
 *        "PK"값이 존재하는 객체에는 사용할 수 없다.
 * 4. 갱신 메서드   (update-)
 *      - 엔티티 데이터를 수정하거나 값을 할당할 때 사용한다.
 * 5. 삭제 메서드   (delete-)
 *      - 데이터를 삭제할 때 사용하지만, 실제로 값을 삭제하는 것이 아닌 deleted 필드를 제어한다.
 * 6. 검색 메서드   (find-, search-)
 *      - 일반적인 select 문을 다루는 메서드이다.
 *        쿼리 파라미터의 개수와 타입이 달라질 때는 search 접두를 사용하여 API 임을 알려야 한다.
 * 3. 검증 메서드   (valid-)
 *      - 엔티티 데이터를 초기화하거나 갱신할 때 "공통적"으로 검증해야 하는 유효성에 관한 메서드이다.
 *        데이터 일관성을 위해 적극 활용하길 바람.
 *        그 외 초기화, 갱신, 삭제 시 특이사항이 따르는 검증절차는 각 메서드 내에서 별도 구현하자.
 * 7. 어댑터 메서드 (-Adaptor)
 *      - "초기화 & 검증", "갱신 & 검증", "삭제 & 검증" 등 여러 메서드를 엮어서 사용하는 최상급 로직이다.
 *
 * [다음 튜닝을 고려하면서 작업할 것] - fetch.lazy
 * https://blog.leocat.kr/notes/2019/11/14/querydsl-result-handling-projection
 * service, 즉 트랜잭션 전역의 바깥(컨트롤러 등)으로 나가는 결과물 중 "Entity"가 있어서는 안 된다.
 *
 * 또한 RuntimeException 및 propagation-REQUIRED 의 일관된 사용으로
 * 트랜잭션의 모든 롤백 맥락은 트랜잭션 전역을 기준으로 한다.
 * 다시 말하면, 로직 내에 예외가 발생할 때 트랜잭션 맥락 내의 모든 DB 변경사항이 롤백된다는 의미이다.
 * 특정 예외에 대해서만 롤백을 방지하고 싶다면 "try/catch"문을 작성하여 트랜잭션을 블로킹하는 것을 시도해 보자.
 *
 * 외 궁금한 사항이 있거나 지적사항이 있을 시 작성자에게 문의 바람.
 *
 */
@Service
@Transactional
public class BulkyRequestServiceImpl implements BulkyRequestService {

    private static final Logger logger = LoggerFactory.getLogger(BulkyRequestServiceImpl.class);

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    BulkyOrderRepository bulkyOrderRepository;

    @Autowired
    BulkyDetailRepository bulkyDetailRepository;

    @Autowired
    AddrService addrService;

    @Autowired
    AreaService areaService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EncryptionMapper encryptionMapper;


    public Long requestAdaptor(@Valid OnlineBulkyRequestDto dto) throws ParseException {

        return null;
    }

    /**
     * 어댑터
     * 접수직원 주도의 배출건 생성 최상위 로직
     * @param dto
     * @param employeeDto
     * @return
     * @throws ParseException
     */
    public Long requestAdaptor(@Valid AdminBulkyRequestDto dto, EmployeeDto employeeDto) throws ParseException {
        logger.info("********************************************");
        logger.info("********************************************");
        logger.info(dto.toString());
        logger.info("********************************************");
        logger.info("********************************************");
        // 접수 직원 엔티티 쿼리
        Employee reqEmployee = employeeService.findByUsername(employeeDto.getUsername());
        // 신청일 생성
        Date orderDatetime = new Date();
        // 접수 직원 정보 검증
        validRequestAdminAndCompany(reqEmployee);
        // 주문 엔티티 생성
        BulkyOrder bulkyOrder = generateRequestOrder(dto, orderDatetime, reqEmployee);
        // 실수거업체, 관할업체, 관할구 초기화
        initJurisdiction(bulkyOrder, reqEmployee);
        // 신청 특성 (결제방법, 방문 유무, 접수 직원, 수납 직원, 결제액, 결제시간) 초기화
        initRequestProperty(bulkyOrder, orderDatetime, reqEmployee, dto.isVisitCollect(), dto.isVisitPay(), dto.getPayType(), dto.getFreeType());
        // 주문건 암호화
        encryptionMapper.doEncryption(bulkyOrder, true);
        // 주문건 저장
        bulkyOrder = bulkyOrderRepository.save(bulkyOrder);
        // 배출건 엔티티 생성
        List<BulkyDetail> bulkyDetails = generateRequestDetails(bulkyOrder, dto.getPriceDtos(), dto.getTotalAmount());
        // 배출건 엔티티 저장
        bulkyDetails = bulkyDetailRepository.saveAll(bulkyDetails);
        // Dto 생성
        List<BulkyDetailDto> bulkyDetailDtos = new ArrayList<>();
        for (BulkyDetail bulkyDetail : bulkyDetails)
            bulkyDetailDtos.add(new BulkyDetailDto(bulkyDetail));
        //
        return bulkyOrder.getCode();
    }

    /**
     * 발생
     * 새로운 배출건을 생성한다
     * @param bulkyOrder
     * @param priceDtos
     * @param dtoTotalAmount
     * @return
     */
    private List<BulkyDetail> generateRequestDetails(BulkyOrder bulkyOrder, List<BulkyPriceDto> priceDtos, BigDecimal dtoTotalAmount) {
        // 품목 리스트가 없을 경우 유효성 검증
        if (priceDtos == null || priceDtos.size() == 0) {
            if (bulkyOrder.isVisitPay()) {
                return new ArrayList<>();   // 방문결제 신청건은 품목이 비어있을 수 있음
            } else {
                throw new RuntimeException("결제할 품목이 존재하지 않습니다"); // 방문결제 건이 아니라면 신청건을 품목이 비어있을 수 없음
            }
        }
        // 아이템 코드 리스트 추출
        List<Long> bulkyItemCodes = new ArrayList<>();
        for (BulkyPriceDto priceDto : priceDtos) {
            if (priceDto == null) {
                throw new RuntimeException("품목정보가 누락되었습니다");
            }
            if (priceDto.getBulkyItemCode() == null) {
                throw new RuntimeException("품목코드가 누락되었습니다");
            }
            if (bulkyItemCodes.contains(priceDto.getBulkyItemCode())) {
                throw new RuntimeException("품목코드가 중복되었습니다");
            } else {
                bulkyItemCodes.add(priceDto.getBulkyItemCode());
            }
        }
        if (bulkyItemCodes.size() != priceDtos.size())
            throw new RuntimeException("품목코드가 중복되었습니다");
        // 실제 아이템 리스트를 추출
        QBulkyItem table = QBulkyItem.bulkyItem;
        BooleanBuilder builder = new BooleanBuilder(); // OR Loop 생성
        for (Long bulkyItemCode : bulkyItemCodes)
            builder.or(table.code.eq(bulkyItemCode));
        List<BulkyItem> bulkyItems = jpaQueryFactory.select(table).from(table)
            .where(builder)
            .where(table.deleted.isFalse())
            .fetch();
        // 삭제된 품목이 있는지 검증
        if (bulkyItemCodes.size() != bulkyItems.size())
            throw new RuntimeException("사용할 수 없는 품목정보가 존재합니다");
        // 마지막 배출번호를 쿼리
        Long maxDetailNo = findMaxDetailNo(bulkyOrder.getOrderNo());
        // 총액 검증 준비
        BigDecimal totalAmountAssemble = BigDecimal.ZERO;    // dto amount 합계
        BigDecimal totalAmountOrigin = BigDecimal.ZERO;      // dto price 합계
        BigDecimal totalAmountReality = BigDecimal.ZERO;     // entity price 합계 (원본)
        // 배출건 생성
        List<BulkyDetail> bulkyDetails = new ArrayList<>();
        for (BulkyPriceDto priceDto : priceDtos) {
            for (BulkyItem bulkyItem : bulkyItems) {
                if (priceDto.getBulkyItemCode().equals(bulkyItem.getCode())) {      // 여기까지 - item "pk"를 매칭
                    totalAmountAssemble.add(priceDto.getAmount());
                    for (int i = 0, n = priceDto.getQuantity(); i < n; i++) {       // 매칭되면 신청 갯수만큼 해당 아이템의 entity 생성
                        maxDetailNo ++; // 배출번호 증가
                        if (maxDetailNo >= 1000) {
                            throw new RuntimeException("한 주문건 내에서 999개 이상은 신청할 수 없습니다");
                        }
                        totalAmountOrigin.add(priceDto.getItemPrice());
                        totalAmountReality.add(bulkyItem.getPrice());
                        bulkyDetails.add(BulkyDetail.builder()
                            .bulkyOrderCode(bulkyOrder.getCode())
                            .cancelOrderCode(null)
                            .empCollectorUsername(null)
                            .empManagerUsername(null)
                            .bulkyItemCode(bulkyItem.getCode())
                            .detailNo(bulkyOrder.getOrderNo() + String.format("%03d", maxDetailNo))
                            .categoryName(bulkyItem.getBulkyCategory().getName())
                            .itemName(bulkyItem.getName())
                            .itemStandard(priceDto.getItemStandard())   // 수정 입력한 규격명을 반영하기 위함
                            .itemPrice(bulkyItem.getPrice())
                            .bulkyState(BulkyState.STANDBY)             // 기본 상태값은 수거대기
                            .updatedDatetime(null)
                            .smsSent(false)
                            .deleted(false)
                            .build()
                        );
                    }
                }
            }
        }
        // 가격 오류 검증
        if (totalAmountAssemble.compareTo(totalAmountReality) != 0  ||
            totalAmountOrigin.compareTo(totalAmountReality) != 0    ||
            dtoTotalAmount.compareTo(totalAmountReality) != 0       ){
            throw new RuntimeException("가격 오류");
        }
        // 반환
        return bulkyDetails;
    }

    /**
     * 생성
     * 주문건 생성
     * 여기서 누락된 부분은 초기화, 갱신 메서드를 만들어 구현해야 한다
     * @param dto               BulkyRequestDto
     * @param orderDatetime     Date
     * @param reqEmployee       Employee (nullable)
     * @return bulkyOrder       BulkyOrder
     * @throws ParseException   SimpleDateFormat
     */
    private BulkyOrder generateRequestOrder(BulkyRequestDto dto, Date orderDatetime, Employee reqEmployee) throws ParseException {
        // 배출예정일시
        String scheduleDatetimeString = dto.getScheduleDate() + " " + dto.getScheduleTime();
        Date scheduleDatetime = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").parse(scheduleDatetimeString);
        // 주문번호 생성
        String orderNo = findNextOrderNo(orderDatetime);
        logger.info("주문번호 :: " + orderNo);
        // Entity 객체 생성
        BulkyOrder bulkyOrder = BulkyOrder.builder()
            // DTO 데이터
            .name(dto.getName())
            .phone(dto.getPhone())
            .position(dto.getPosition())
            .smsAgree(dto.isSmsAgree())
            .addrJibun(dto.getAddrJibun())
            .addrRoad(dto.getAddrRoad())
            .addrDetail(dto.getAddrDetail())
            .zipCode(dto.getZipCode())
            .sido(dto.getSido())
            .sgg(dto.getSgg())
            .bemd(dto.getBemd())
            .hemd(dto.getHemd())
            .lat(dto.getLat())
            .lng(dto.getLng())
            .orderDatetime(orderDatetime)
            .orderNo(orderNo)
            .build();
        // 배출예정일시, 색변 기준일 갱신
        updateScheduleDateTime(bulkyOrder, scheduleDatetime);

        return bulkyOrder;
    }

    /**
     * 검색
     * 주문번호를 기준으로 마지막 배출번호를 반환함
     * @param orderNo               주문번호
     * @return                      해당 주문번호가 가진 마지막 (부분)배출번호
     */
    private Long findMaxDetailNo(String orderNo) {
        QBulkyDetail table = QBulkyDetail.bulkyDetail;
        String maxDetailNo = jpaQueryFactory.select(table.detailNo.max().substring(11, 3)).from(table)
            .where(table.detailNo.like(orderNo + "%")).fetchOne();
        long maxDetailNoLong;
        if (maxDetailNo == null) {
            maxDetailNoLong = 0;
        } else {
            maxDetailNoLong = Long.parseLong(maxDetailNo.substring(10));
        }
        return maxDetailNoLong;
    }

    /**
     * 검색
     * 입력된 일자의 마지막 주문번호를 검색하여 다음 주문번호를 반환함
     * @param orderDatetime         주문날짜
     * @return                      해당 날짜에서 생성할 수 있는 가장 빠른 주문번호
     */
    private String findNextOrderNo(Date orderDatetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderDatetime);
        QBulkyOrder table = QBulkyOrder.bulkyOrder;
        String maxOrderNo = jpaQueryFactory.select(table.orderNo.max()).from(table)
            .where(table.orderDatetime.year().eq(calendar.get(Calendar.YEAR)))
            .where(table.orderDatetime.month().eq(calendar.get(Calendar.MONTH) + 1))
            .where(table.orderDatetime.dayOfMonth().eq(calendar.get(Calendar.DAY_OF_MONTH)))
            .fetchOne();
        long maxOrderNoLong;
        if (maxOrderNo == null) {
            maxOrderNoLong = 0;
        } else {
            maxOrderNoLong = Long.parseLong(maxOrderNo.substring(6));
            if (maxOrderNoLong >= 9999) {
                throw new RuntimeException("오늘은 더이상 주문번호를 생성할 수 없습니다. 관리자에게 문의해주세요");
            }
        }
        return String.format("%s%04d", DateFormatHelper.formatDate6(orderDatetime), maxOrderNoLong + 1);
    }

    /**
     * 초기화
     * 결제방법, 방문 유무, 접수 직원, 수납 직원, 결제액, 결제시간
     * @param bulkyOrder        BulkyOrder
     * @param orderDatetime     주문일시
     * @param reqEmployee       접수원  (nullable)
     * @param visitCollect      방문수거
     * @param visitPay          방문결제
     * @param payType           결제타입
     * @param freeType          면제유형
     */
    private void initRequestProperty(@NotNull BulkyOrder bulkyOrder, Date orderDatetime, Employee reqEmployee, boolean visitCollect, boolean visitPay, PayType payType, FreeType freeType) {
        if (bulkyOrder.getCode() != null) {
            throw new RuntimeException("초기화 할 수 없는 객체");
        }
        // 접수 업체 정보의 등록
        CompanyType reqCompanyType;
        if (reqEmployee == null) {
            reqCompanyType = CompanyType.SYSTEM;
        } else {
            validRequestAdminAndCompany(reqEmployee);
            reqCompanyType = reqEmployee.getCompany().getType();
        }
        // 초기화 유효성 검증
        if (reqCompanyType == CompanyType.SYSTEM)
            throw new RuntimeException("온라인 신청건은 결제방법을 초기화할 수 없습니다");
        // payGroup 추출
        PayGroup payGroup = payType != null ? payType.getPayGroup() : null;
        // 결제, 수거 방법의 기본 유효성 검증
        validRequestProperty(reqCompanyType, visitCollect, visitPay, payType, freeType);
        // 접수 직원, 수납 직원, 결제일시의 초기화
        String empClerkUsername = reqEmployee.getUsername();
        String empCashierUsername = null;
        Date payDatetime = null;
        if (payType != null) {              // NPE
            if (payType.isInstantPay()) {   // 즉시결제타입인 경우 할당
                empCashierUsername = reqEmployee.getUsername();
                payDatetime = orderDatetime;
            }
        }
        // 세팅
        bulkyOrder.initRequestProperty(
            visitCollect,
            visitPay,
            payGroup,           // nullable
            payType,            // nullable
            freeType,           // nullable
            empClerkUsername,           // nullable
            empCashierUsername,         // nullable
            null,      // default null
            payDatetime,        // nullable
            false,     // init false
            false       // init false
        );
    }

    /**
     * 검증
     * 방문수거, 방문결제, 결제방법, 면제사유 등의 기본 유효성 검증
     * @param reqCompanyType        (not null)
     * @param visitCollect
     * @param visitPay
     * @param payType               (nullable)
     * @param freeType              (nullable)
     */
    private void validRequestProperty(CompanyType reqCompanyType, boolean visitCollect, boolean visitPay, PayType payType, FreeType freeType) {
        // 소속집단 유형
        if (reqCompanyType == null)
            throw new RuntimeException("소속정보 누락됨");
        // 요청집단에 허용된 결제방법인지 검증
        List<PayType> payTypeList = reqCompanyType.getPayTypeList();
        if (!payTypeList.contains(payType)) {
            if (!(visitPay && payType == null)) { // 방문결제 - 결제타입이 null일 수 있음
                throw new RuntimeException("허용되지 않은 결제방법입니다");
            }
        }
        // 수수료 면제가 아니면서 면제사유를 갖고 있는지 검증
        if (payType != PayType.OFF_FREE && freeType != null)
            throw new RuntimeException("수수료 면제가 아닌 경우 면제사유는 함께 입력될 수 없습니다");
        // 수수료 면제이면서 면제사유가 없는지 검증
        if (payType == PayType.OFF_FREE && freeType == null)
            throw new RuntimeException("수수료 면제인 경우 면재사유가 입력되어야 합니다");
        // 방문결제이면서 PG 결제인 경우
        if (payType != null) {
            if ((visitPay && payType.getPayGroup() == PayGroup.KG_ALLAT) || (visitPay && payType.getPayGroup() == PayGroup.TOSS_PAY))
                throw new RuntimeException("방문결제 건은 PG사 결제를 할 수 없습니다");
        }
        // 온라인결제이면서 면제 사유를 가지고 있는 경우
        if (reqCompanyType == CompanyType.SYSTEM && freeType != null)
            throw new RuntimeException("온라인 신청건은 면제사유를 입력할 수 없습니다");
        // 방문결제가 아니면서 결제방법이 누락된 경우
        if (!visitPay && payType == null)
            throw new RuntimeException("일반결제 신청건은 결제방법을 지정해야 합니다");
        /*모바일 접수(방문결제)의 경우 즉시 결제방법 입력이 가능함
        if (visitPay && payType != null) {}*/
    }

    /**
     * 검증
     * 접수 직원의 유효성을 검증
     * @param employee
     */
    private void validRequestAdminAndCompany(Employee employee) {
        if (employee == null)
            throw new RuntimeException("존재하지 않는 계정정보입니다");
        if (employee.isDeleted())
            throw new RuntimeException("삭제된 계정입니다");
        if (employee.getCompany() == null)
            throw new RuntimeException("소속 정보가 존재하지 않는 계정입니다");
        if (employee.getCompany().isDeleted())
            throw new RuntimeException("소속 집단이 삭제된 계정입니다");
    }

    /**
     * 갱신
     * 엔티티에 배출예약일, 마커 색변 기준일을 세팅한다
     * @param bulkyOrder
     * @param scheduleDatetime
     * @throws ParseException
     */
    private void updateScheduleDateTime(BulkyOrder bulkyOrder, Date scheduleDatetime) throws ParseException {

        String markerWarnString = (HolidayUtil.isHoliday(DateFormatHelper.formatDate8(scheduleDatetime), 2));
        String markerExcessString = (HolidayUtil.isHoliday(DateFormatHelper.formatDate8(scheduleDatetime), 4));

        Date markerWarn = new SimpleDateFormat("yyyyMMdd").parse(markerWarnString);
        Date markerExcess = new SimpleDateFormat("yyyyMMdd").parse(markerExcessString);

        bulkyOrder.updateScheduleDateTime(scheduleDatetime, markerWarn, markerExcess);
    }

    /**
     * 초기화
     * [지정 수거업체, 담당 업체 배열, 구 단위 관할] 데이터 등록
     * @param bulkyOrder        BulkyOrder
     * @param reqEmployee       Employee (nullable)
     */
    private void initJurisdiction(BulkyOrder bulkyOrder, Employee reqEmployee) {
        if (bulkyOrder.getCode() != null) {
            throw new RuntimeException("초기화 할 수 없는 객체");
        }
        // 행정구역 정보
        Addr addr = addrService.findByEmds(bulkyOrder.getBemd(), bulkyOrder.getHemd());
        if (addr == null)
            throw new RuntimeException("시스템 관할 지역이 아닙니다");
        // 관할구역 정보
        Long compCleanCode = null;   // 실수거 업체
        String companies = "";      // 관할구역 업체들
        // 내 소속 집단이 수거업체인 경우 할당
        if (reqEmployee != null) {
            if (reqEmployee.getCompany() != null) {
                if (reqEmployee.getCompany().getType() == CompanyType.CLEAN_COMPANY) {
                    compCleanCode = reqEmployee.getCompany().getCode(); //소속 집단이 수거업체일 경우 바로 할당
                    companies = String.valueOf(reqEmployee.getCompany().getCode());
                }
            } else {
                throw new RuntimeException("어디에도 소속되지 않은 계정으로는 주문건을 작성할 수 없습니다");
            }
        }
        // 내 소속 집단이 없거나 수거업체가 아닌 경우 ... 미할당 / 관할구역 검색
        if (compCleanCode == null) {
            List<Area> areas = areaService.findByHemdAndWasteType(bulkyOrder.getHemd(), WasteType.BULKY);
            // 관할 업체가 없음
            if (areas.size() == 0) {
                throw new RuntimeException("해당 지역을 관할중인 업체가 존재하지 않습니다");
            // 하나의 업체만이 관할함 < 지정 할당
            } else if (areas.size() == 1) {
                compCleanCode = areas.get(0).getCompany().getCode();
                companies = String.valueOf(areas.get(0).getCompanyCode());
            // 다수의 업체가 관할함 < 수배
            } else {
                compCleanCode = null;
                for (Area area : areas) {
                    if (!StringUtils.isBlank(companies)) {
                        companies += ",";
                    }
                    companies += area.getCompanyCode();
                }
            }
        }
        bulkyOrder.initJurisdiction(compCleanCode, companies, addr.getDistrict().getName());
    }




}
