package com.apsol.ecopass.exception;

/**
 * 인증 실패에 대한 예외처리
 */
public class AuthorizeException extends Exception{

    private static final long serialVersionUID = 7880753668764062550L;

    public AuthorizeException(String msg) {
        super(msg);
    }

}