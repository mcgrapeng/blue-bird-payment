package com.zhangpeng.payment.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * <b>功能说明:MD5签名工具类
 * </b>
 */
@Slf4j
public class MD5Utils {

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private MD5Utils() {
    }

    private static final String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String encode(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = md5.digest(password.getBytes("utf-8"));
            String passwordMD5 = byteArrayToHexString(byteArray);
            return passwordMD5;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return password;
    }

    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        for (byte b : byteArray) {
            sb.append(byteToHexChar(b));
        }
        return sb.toString();
    }

    private static Object byteToHexChar(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hex[d1] + hex[d2];
    }
}