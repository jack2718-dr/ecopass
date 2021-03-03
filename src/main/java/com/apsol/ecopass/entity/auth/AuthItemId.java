package com.apsol.ecopass.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthItemId implements Serializable {

    private static final long serialVersionUID = -326574580112634525L;

    private Long authCode;

    private String roleName;

}
