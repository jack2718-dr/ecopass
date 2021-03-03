package com.apsol.ecopass.core.security;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResource {

    /**
     * 에러코드
     */
    private final int status;

    /**
     * 에러 메시지
     */
    private final String message;

}
