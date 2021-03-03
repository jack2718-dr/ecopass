package com.apsol.ecopass.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class AES256 {

    /**
     * 사용하지 않음
     * Spring Security 의 queryableText로 대체합니다.
     *
     *      TextEncryptor textEncryptor = Encryptors.queryableText("password", "23a35de329a183bf");
     *      String enc = textEncryptor.encrypt("ㅎㅇ");
     *      String enc3 = textEncryptor.encrypt("ㅎㅇ");
     *      System.out.println(enc);
     *      System.out.println(enc3);
     *
     */

    private final static String KEY_STRING = "a0p7s0o2l000@$&^d8d2sf3H3m602(3g"; //32bit
    private final static String IV = "dg!@fMd#$%fhkTwq";
    private final Key key;

    public AES256() {
        byte[] keyData = KEY_STRING.getBytes(StandardCharsets.UTF_8);

        if (keyData.length != 32)
            throw new RuntimeException("암호키 길이 오류");
        if (IV.length() != 16)
            throw new RuntimeException("암호키 길이 오류");

        key = new SecretKeySpec(keyData, "AES");
    }

    public String encrypt(String str) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
        byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));

        return new String(Base64.encodeBase64(encrypted));
    }

    public String decrypt(String str) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());

        return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
    }

}
