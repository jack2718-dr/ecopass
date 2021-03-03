package com.apsol.ecopass.entity.area;

import com.apsol.ecopass.enums.WasteType;
import com.apsol.ecopass.entity.member.Company;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "areas")
@IdClass(AreaId.class)
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class Area {

    /**
     * 행정동 - PK
     */
    @Id
    @Column(name = "hemd", nullable = false, updatable = false)
    private String hemd;

    /**
     * 폐기물 종류 - PK
     */
    @Id
    @Column(name = "wasteType", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private WasteType wasteType;


    /**
     * 집단 FK - PK
     */
    @Id
    @Column(name = "company", nullable = false, updatable = false)
    private Long companyCode;


    /**
     * 집단 FK
     */
    @ManyToOne
    @JoinColumn(name = "company", nullable = false, insertable = false, updatable = false)
    private Company company;


}
