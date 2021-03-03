package com.apsol.ecopass.dto.common.bulky;

import com.apsol.ecopass.entity.Photo;
import com.apsol.ecopass.entity.bulky.BulkyDetail;
import com.apsol.ecopass.entity.bulky.BulkyItem;
import com.apsol.ecopass.entity.bulky.BulkyOrder;
import com.apsol.ecopass.entity.member.Employee;
import com.apsol.ecopass.enums.BulkyState;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class BulkyDetailDto {

    private Long code;

    private String detailNo;

    private String categoryName;

    private String itemName;

    private String itemStandard;

    private BigDecimal itemPrice;

    private BulkyState bulkyState;

    private Date updatedDatetime;

    private Boolean smsSent;

    private Boolean deleted;


    private Long bulkyOrderCode;

    private Long cancelOrderCode;

    private Long photoCode;

    private String empCollectorUsername;

    private String empManagerUsername;

    private Long bulkyItemCode;


    public BulkyDetailDto(BulkyDetail bulkyDetail) {
        this.code = bulkyDetail.getCode();
        this.detailNo = bulkyDetail.getDetailNo();
        this.categoryName = bulkyDetail.getCategoryName();
        this.itemName = bulkyDetail.getItemName();
        this.itemStandard = bulkyDetail.getItemStandard();
        this.itemPrice = bulkyDetail.getItemPrice();
        this.bulkyState = bulkyDetail.getBulkyState();
        this.updatedDatetime = bulkyDetail.getUpdatedDatetime();
        this.smsSent = bulkyDetail.isSmsSent();
        this.deleted = bulkyDetail.isDeleted();

        this.bulkyOrderCode = bulkyDetail.getBulkyOrderCode();
        this.cancelOrderCode = bulkyDetail.getCancelOrderCode();
        this.photoCode = bulkyDetail.getPhotoCode();
        this.empCollectorUsername = bulkyDetail.getEmpCollectorUsername();
        this.empManagerUsername = bulkyDetail.getEmpManagerUsername();
        this.bulkyItemCode = bulkyDetail.getBulkyItemCode();
    }

}
