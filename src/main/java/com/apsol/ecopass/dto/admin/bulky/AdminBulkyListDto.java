package com.apsol.ecopass.dto.admin.bulky;

import com.apsol.ecopass.annotations.TwoWayEncryption;
import com.apsol.ecopass.dto.common.bulky.BulkyDetailDto;
import com.apsol.ecopass.entity.bulky.BulkyDetail;
import com.apsol.ecopass.enums.FreeType;
import com.apsol.ecopass.enums.PayGroup;
import com.apsol.ecopass.enums.PayType;
import com.apsol.ecopass.util.DateFormatHelper;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class AdminBulkyListDto {
    /**
     * Only for search option
     */

    private Date from;

    private Date to;

    private PayGroup payGroup;

    private PayType payType;

    /**
     * Data
     */
    private Long code;

    private String empClerkUsername;

    private String empCashierUsername;

    private String districtName;

    private Long paymentCode;

    private Long compCleanCode;

    private String orderNo;

    private Date payDatetime;

    private String schedule;

    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String name;

    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String phone;

    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String addrJibun;

    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String addrRoad;

    @TwoWayEncryption //////////////////////////////////////////////////////////////////////
    private String addrDetail;

    private String position;

    private String bemd;

    private String hemd;

    private String payTypeTitle;

    private String freeTypeTitle;

    private Boolean canceled;

    private Boolean visitPay;

    private Boolean visitCollect;

    private Boolean smsAgree;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    private List<BulkyDetailDto> details = new ArrayList<>();

    @QueryProjection
    public AdminBulkyListDto(Long code, String empClerkUsername, String empCashierUsername, String districtName, Long paymentCode, Long compCleanCode, String orderNo, Date payDatetime, Date scheduleDatetime, String name, String phone, String addrJibun, String addrRoad, String addrDetail, String position, String bemd, String hemd, /*PayGroup payGroup,*/ PayType payType, FreeType freeType, boolean canceled, boolean visitPay, boolean visitCollect, boolean smsAgree, BigDecimal totalAmount/*, List<BulkyDetail> bulkyDetails*/) {
        this.code = code;
        this.empClerkUsername = empClerkUsername;
        this.empCashierUsername = empCashierUsername;
        this.districtName = districtName;
        this.paymentCode = paymentCode;
        this.compCleanCode = compCleanCode;
        this.orderNo = orderNo;
        this.payDatetime = payDatetime;
        this.schedule = DateFormatHelper.formatHalfDatetime(scheduleDatetime);
        this.name = name;
        this.phone = phone;
        this.addrJibun = addrJibun;
        this.addrRoad = addrRoad;
        this.addrDetail = addrDetail;
        this.position = position;
        this.bemd = bemd;
        this.hemd = hemd;
        if (payType != null)
            this.payTypeTitle = payType.getTitle();
        if (freeType != null)
            this.freeTypeTitle = freeType.getTitle();
        this.canceled = canceled;
        this.visitPay = visitPay;
        this.visitCollect = visitCollect;
        this.smsAgree = smsAgree;
        this.totalAmount = totalAmount;

//        if (details != null) {
//            for (BulkyDetail bulkyDetail : bulkyDetails)
//                this.details.add(new BulkyDetailDto(bulkyDetail));
//        }

    }

}
