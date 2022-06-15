package org.ryuji.ddd.demo.project.util;

import cn.hutool.crypto.digest.MD5;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class PasswordUtil {

    public static String getHashPassword(String user, String pass, String salt) {
        String str = String.format("%s,%s,%s", user, pass, salt);
        return MD5.create().digestHex(str);
    }

    public static boolean matchPassword(String user, String pass, String hashedpass, String salt) {
        String hashPasswordTmp = getHashPassword(user, pass, salt);
        return hashedpass.equals(hashPasswordTmp);
    }

    public static boolean match(Integer userId, String password, String hashedPassword, String salt) {
        String str = String.format("%d,%s,%s", userId, password, salt);
        return hashedPassword.equals(MD5.create().digestHex(str));
    }

    public static String genPasswordSalt() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }

    public static String genUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }

    public static String getSalt() {
        return RandomStringUtils.random(6);
    }
}
