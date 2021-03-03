package com.apsol.ecopass.entity.area;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "addrs")
@IdClass(AddrId.class)
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class Addr {

    /**
     * 행정동 - PK
     */
    @Id
    @Column(name = "hemd", nullable = false)
    private String hemd;

    /**
     * 법정동 - PK
     */
    @Id
    @Column(name = "bemd", nullable = false)
    private String bemd;

    /**
     * 지역 FK
     */
    @ManyToOne
    @JoinColumn(name = "district", nullable = false, updatable = false)
    private District district;

}
