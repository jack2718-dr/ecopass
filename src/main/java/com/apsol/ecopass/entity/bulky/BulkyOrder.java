package com.apsol.ecopass.entity.bulky;

import com.apsol.ecopass.annotations.TwoWayEncryption;
import com.apsol.ecopass.enums.FreeType;
import com.apsol.ecopass.enums.PayGroup;
import com.apsol.ecopass.enums.PayType;
import com.apsol.ecopass.entity.Payment;
import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.entity.member.Company;
import com.apsol.ecopass.entity.member.Employee;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "bulky_orders")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class BulkyOrder {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    /**
     * 접수 직원 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_clerk", insertable = false, updatable = false)
    private Employee empClerk;

    @Column(name = "emp_clerk")
    private String empClerkUsername;

    /**
     * 수납 직원 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_cashier", insertable = false, updatable = false)
    private Employee empCashier;

    @Column(name = "emp_cashier")
    private String empCashierUsername;

    /**
     * 구 관할 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district", insertable = false, updatable = false)
    private District district;

    @Column(name = "district", nullable = false, updatable = false)
    private String districtName;

    /**
     * 결제 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment", insertable = false, updatable = false)
    private Payment payment;

    @Column(name = "payment")
    private Long paymentCode;

    /**
     * 실 수거업체
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_clean", insertable = false, updatable = false)
    private Company compClean;

    @Column(name = "comp_clean")
    private Long compCleanCode;

    /**
     * 담당 업체 FK 배열
     */
    @Column(name = "companies")
    @Setter(AccessLevel.NONE)
    private String companies;

    /**
     * 주문번호
     */
    @Column(name = "order_no", nullable = false, updatable = false, unique = true, length = 10)
    @Setter(AccessLevel.NONE)
    private String orderNo;

    /**
     * 주문일시
     */
    @Column(name = "order_datetime", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDatetime;

    /**
     * 결제완료일시
     */
    @Column(name = "pay_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDatetime;

    /**
     * 배출예정일시
     */
    @Column(name = "schedule_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "배출 예정 일자는 필수항목입니다.")
    private Date scheduleDatetime;

    /**
     * 배출자 이름
     */
    @Column(name = "name", nullable = false)
    @NotNull(message = "이름은 필수항목 입니다.")
    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String name;

    /**
     * 전화번호(연락처)
     */
    @Column(name = "phone", nullable = false)
    @NotNull(message = "전화번호는 필수항목 입니다.")
    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String phone;

    /**
     * 지번 주소
     */
    @Column(name = "addr_jibun", nullable = false)
    @NotNull(message = "주소는 null일수 없습니다.")
    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String addrJibun;

    /**
     * 도로명 주소
     */
    @Column(name = "addr_road", nullable = false)
    @NotNull(message = "주소는 null일수 없습니다.")
    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String addrRoad;

    /**
     * 도로명 주소
     */
    @Column(name = "addr_detail", nullable = false)
    @NotNull(message = "상세 주소는 필수항목 입니다.")
    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String addrDetail;

    /**
     * 상세 배출 위치
     */
    @NotNull(message = "상세 위치는 필수항목 입니다.")
    @Column(name = "position", nullable = false)
    private String position;

    /**
     * 우편번호
     */
    @NotNull(message = "우편번호는 null일수 없습니다.")
    @Column(name = "zip_code", nullable = false)
    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String zipCode;

    /**
     * 시도
     */
    @NotNull(message = "시도는 null일수 없습니다.")
    @Column(name = "sido", nullable = false)
    private String sido;

    /**
     * 시군구
     */
    @NotNull(message = "시군구는 null일수 없습니다.")
    @Column(name = "sgg", nullable = false)
    private String sgg;

    /**
     * 법정동
     */
    @NotNull(message = "법정동은 null일수 없습니다.")
    @Column(name = "bemd", nullable = false)
    private String bemd;

    /**
     * 행정동
     */
    @NotNull(message = "행정동은 필수항목 입니다.")
    @Column(name = "hemd", nullable = false)
    private String hemd;

    /**
     * 위도
     */
    @NotNull(message = "위도는 null일수 없습니다.")
    @Column(name = "lat", nullable = false)
    private Double lat;

    /**
     * 경도
     */
    @NotNull(message = "경도는 null일수 없습니다.")
    @Column(name = "lng", nullable = false)
    private Double lng;

    /**
     * 마커 - 경고
     */
    @Column(name = "marker_warn", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date markerWarn;

    /**
     * 마커 - 초과
     */
    @Column(name = "marker_excess", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date markerExcess;

    /**
     * 결제사
     */
    @Column(name = "pay_group")
    @Enumerated(EnumType.STRING)
    private PayGroup payGroup;

    /**
     * 결제 유형
     */
    @Column(name = "pay_type")
    @Enumerated(EnumType.STRING)
    private PayType payType;

    /**
     * 수수료 면제 유형
     */
    @Column(name = "free_type")
    @Enumerated(EnumType.STRING)
    private FreeType freeType;

    /**
     * 취소건 구분
     */
    @Column(name = "canceled", nullable = false, updatable = false)
    private boolean canceled = false;

    /**
     * 방문결제 유무
     */
    @Column(name = "visit_pay", nullable = false)
    private boolean visitPay = false;

    /**
     * 방문수거 유무
     */
    @Column(name = "visit_collect", nullable = false)
    private boolean visitCollect = false;

    /**
     * SMS 수신동의
     */
    @Column(name = "sms_agree", nullable = false)
    private boolean smsAgree = false;

    /**
     * 삭제 유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;




    /**
     * 초기화
     * 결제 방법, 방문 유무, 접수 직원, 수납 직원, 결제액, 결제시간
     */
    public void initRequestProperty(boolean visitCollect, boolean visitPay, PayGroup payGroup, PayType payType, FreeType freeType, String empClerkUsername, String empCashierUsername, Long paymentCode, Date payDatetime, boolean canceled, boolean deleted) {
        if (this.code != null) {
            throw new RuntimeException("초기화 할 수 없는 객체");
        }
        this.visitCollect = visitCollect;
        this.visitPay = visitPay;
        this.payGroup = payGroup;
        this.payType = payType;
        this.freeType = freeType;
        this.empClerkUsername = empClerkUsername; // updatable = false
        this.empCashierUsername = empCashierUsername;
        this.paymentCode = paymentCode;
        this.payDatetime = payDatetime;
        this.canceled = canceled;
        this.deleted = deleted;
    }

    /**
     * 초기화
     * 지정업체, 관할 업체 목록, 관할 구 데이터
     */
    public void initJurisdiction(Long compCleanCode, @NotNull String companies, @NotNull String districtName) {
        if (this.code != null) {
            throw new RuntimeException("초기화 할 수 없는 객체");
        }
        this.compCleanCode = compCleanCode;
        this.companies = companies;
        this.districtName = districtName;
    }

    /**
     * 갱신
     * 마커 색변 기준일
     */
    public void updateScheduleDateTime(@NotNull Date scheduleDatetime, @NotNull Date markerWarn, @NotNull Date markerExcess) {
        this.scheduleDatetime = scheduleDatetime;
        this.markerWarn = markerWarn;
        this.markerExcess = markerExcess;
    }



}
