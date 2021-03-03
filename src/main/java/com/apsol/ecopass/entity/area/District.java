package com.apsol.ecopass.entity.area;

import com.apsol.ecopass.entity.member.Company;
import com.apsol.ecopass.entity.member.Employee;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "districts")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class District {

    /**
     * PK
     */
    @Id
    @Column(name = "name", nullable = false)
    private String name;

}

