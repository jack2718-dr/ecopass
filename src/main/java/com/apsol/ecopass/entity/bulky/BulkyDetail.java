package com.apsol.ecopass.entity.bulky;

import com.apsol.ecopass.enums.BulkyState;
import com.apsol.ecopass.entity.Photo;
import com.apsol.ecopass.entity.member.Employee;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bulky_details")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class BulkyDetail {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long code;

    /**
     * 주문(상위)건 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulky_order", insertable = false, updatable = false)
    private BulkyOrder bulkyOrder;

    @Column(name = "bulky_order", nullable = false)
    private Long bulkyOrderCode;

    /**
     * 주문(상위)건 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancel_order", insertable = false, updatable = false)
    private BulkyOrder cancelOrder;

    @Column(name = "cancel_order")
    private Long cancelOrderCode;

    /**
     * 사진 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo", insertable = false, updatable = false)
    private Photo photo;

    @Column(name = "photo")
    private Long photoCode;

    /**
     * 수거 직원 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_collector", insertable = false, updatable = false)
    private Employee empCollector;

    @Column(name = "emp_collector")
    private String empCollectorUsername;

    /**
     * 결재 직원 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_manager", insertable = false, updatable = false)
    private Employee empManager;

    @Column(name = "emp_manager")
    private String empManagerUsername;

    /**
     * 대형폐기물 품목 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulky_item", insertable = false, updatable = false)
    private BulkyItem bulkyItem;

    @Column(name = "bulky_item", nullable = false)
    private Long bulkyItemCode;

    /**
     * 배출번호
     */
    @Column(name = "detail_no", nullable = false, updatable = false, unique = true, length = 13)
    private String detailNo;

    /**
     * 품목 분류
     */
    @Column(name = "category_name", nullable = false, updatable = false)
    private String categoryName;

    /**
     * 품목 이름
     */
    @Column(name = "item_name", nullable = false, updatable = false)
    private String itemName;

    /**
     * 품목 규격
     */
    @Column(name = "item_standard", nullable = false, updatable = false)
    private String itemStandard;

    /**
     * 품목 가격
     */
    @Column(name = "item_price", nullable = false, updatable = false)
    private BigDecimal itemPrice = BigDecimal.ZERO;

    /**
     * 상태값
     */
    @Column(name = "bulky_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private BulkyState bulkyState;

    /**
     * 업데이트 시간
     */
    @Column(name = "updated_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDatetime = new Date();

    /**
     * 업데이트 시간
     */
    @Column(name = "sms_sent", nullable = false)
    private boolean smsSent = false;

    /**
     * 삭제 유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}
