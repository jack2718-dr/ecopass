package com.apsol.ecopass.entity.member;

import com.apsol.ecopass.entity.area.District;
import com.apsol.ecopass.enums.CompanyType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "companies")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class Company {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long code;

    /**
     * 지역 FK
     */
    @ManyToOne
    @JoinColumn(name = "district", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private District district;

    @Column(name = "district", nullable = false, updatable = false)
    private String districtName;

    /**
     * 집단 유형 (enum)
     */
    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private CompanyType type;

    /**
     * 이름
     */
    @Column(name = "name", nullable = false)
    private String name = "";

    /**
     * 대표번호
     */
    @Column(name = "phone", nullable = false)
    private String phone = "";

    /**
     * 삭제유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;



    public Company(District district, CompanyType type) {
        this.district = district;
        this.type = type;
    }

}
