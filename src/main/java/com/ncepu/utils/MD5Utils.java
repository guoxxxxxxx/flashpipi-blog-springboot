package com.ncepu.utils;

import java.security.MessageDigest;

public class MD5Utils {
    /**
     * 对密码进行加密
     * @param s 所需加密的密码
     * @return 加密后的密码
     */
    public static String getMD5(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b : digest){
                sb.append(Character.forDigit((b >> 4) & 0xf, 16));
                sb.append(Character.forDigit(b & 0xf, 16));
            }
            return sb.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
