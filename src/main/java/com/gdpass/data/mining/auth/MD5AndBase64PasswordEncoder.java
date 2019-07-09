package com.gdpass.data.mining.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Bennu
 * @date 2019-04-01
 * 
 * 将原始密码rawPassword MD5加密后，进行Base64转化为字符串
 * 
 * */
public class MD5AndBase64PasswordEncoder implements PasswordEncoder {
    private static final Logger logger = LoggerFactory.getLogger(MD5AndBase64PasswordEncoder.class);
    
    /**
     * 将原始密码rawPassword MD5加密后，进行Base64转化为字符串 
     * */
    public String encode(CharSequence rawPassword) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        byte[] md5 = md.digest(rawPassword.toString().getBytes());
        return Base64.getEncoder().encodeToString(md5);
    }
    
    /**
     * rawPassword 就是明文密码
     * encodedPassword  是rawPassword 的加密
     * 原始密码rawPassword  和加密密码   encodedPassword 是否匹配
     * */
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.encode(rawPassword).equals(encodedPassword);
    }

}
