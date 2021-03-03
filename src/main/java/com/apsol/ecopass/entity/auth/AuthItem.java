package com.apsol.ecopass.entity.auth;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "auth_items")
@IdClass(AuthItemId.class)
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class AuthItem {

    /**
     * 권한 FK - PK
     */
    @ManyToOne
    @JoinColumn(name = "auth", nullable = false, insertable = false, updatable = false)
    private Auth auth;

    @Id
    @Column(name = "auth", nullable = false)
    private Long authCode;

    /**
     * 역할 FK - PK
     */
    @ManyToOne
    @JoinColumn(name = "role", nullable = false, insertable = false, updatable = false)
    private Role role;

    @Id
    @Column(name = "role", nullable = false)
    private String roleName;


    public AuthItem(Auth auth, Role role) {
        this.auth = auth;
        this.authCode = auth.getCode();

        this.role = role;
        this.roleName = role.getName();
    }

}
