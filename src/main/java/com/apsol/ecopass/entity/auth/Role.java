package com.apsol.ecopass.entity.auth;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class Role {

    /**
     * PK
     */
    @Id
    @Column(name = "name", nullable = false, updatable = false, length = 50)
    private String name;

    /**
     * 설명
     */
    @Column(name = "memo", nullable = false, updatable = false)
    private String memo = "";

    /**
     * 삭제유무
     */
    @Column(name = "deleted", nullable = false, updatable = false)
    private boolean deleted = false;

}
