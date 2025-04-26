package com.ateam.jjimppong_back.common.util;

import java.util.Random;

public class TemporaryPasswordUtil {
    
    // 임시 비밀번호 8자리 생성
    // -A~Z, a~z, 0~9의 8자리 임의의 문자열
    public static String createCodeNumber() {
        char[] temporaryPassword = new char[8];
        Random random = new Random();

        for (int index = 0; index < temporaryPassword.length; index++) {
            int flag = random.nextInt(3); // 0: 숫자, 1: 대문자, 2: 소문자
            if (flag == 0) {
                temporaryPassword[index] = (char)(random.nextInt(10) + 48);  // 숫자 0~9
            } else if (flag == 1) {
                temporaryPassword[index] = (char)(random.nextInt(26) + 65);  // 대문자 A~Z
            } else {
                temporaryPassword[index] = (char)(random.nextInt(26) + 97);  // 소문자 a~z
            }
        }
        return new String(temporaryPassword);
    }
}
