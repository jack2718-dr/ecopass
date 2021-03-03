package com.apsol.ecopass.entity.member;

import com.apsol.ecopass.entity.auth.Auth;
import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class Employee {

    /**
     * PK
     * 단방향 암호화
     */
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 권한 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth", insertable = false, updatable = false)
    private Auth auth;

    @Column(name = "auth")
    private Long authCode;

    /**
     * 집단 FK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company", nullable = false, insertable = false, updatable = false)
    private Company company;

    @Column(name = "company", nullable = false)
    private Long companyCode;

    /**
     * 패스워드
     * 단방향 암호화
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * 이름
     * 양방향 암호화
     */
    //@Crypto(cryptoType = CryptoType.TWO_WAY)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * 전화번호
     * 양방향 암호화
     */
    @Column(name = "phone", length = 255)
    private String phone;

    /**
     * 이메일
     * 양방향 암호화
     */
    @Column(name = "email", length = 255)
    private String email;

    /**
     * 마스터 권한
     */
    @Column(name = "master", nullable = false, insertable = false, updatable = false)
    private boolean master = false;

    /**
     * 삭제유무
     */
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

}
