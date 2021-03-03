package com.apsol.ecopass.core.security.model;

import lombok.*;

/**
 * 원격 로그인 요청시 사용하는 객체
 * //@RequestBody
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {

    String username;

    String password;

}
