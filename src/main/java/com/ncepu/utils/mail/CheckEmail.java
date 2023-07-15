package com.ncepu.utils.mail;

public class CheckEmail {

    /**
     * 检查邮箱格式是否正确
     * @return ture or false
     */
    public static boolean isEmail(String email) {
        if (email == null || email.equals("")){
            return false;
        }
        else {
            return true;
        }
    }
}
