package com.apsol.ecopass.entity.auth;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "auths")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class Auth {

    /**
     * PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long code;

    /**
     * 이름
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name = "";

    /**
     * 삭제유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}
