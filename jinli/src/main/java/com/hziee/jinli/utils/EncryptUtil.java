package com.hziee.jinli.utils;

import java.security.MessageDigest;

/**
 *
 * Encrypt 工具类
 *
 */
public class EncryptUtil {
    public static String String_Encrypt(String str){
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {

            byte[] btInput = str.getBytes();

            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            mdInst.update(btInput);

            byte[] md = mdInst.digest();

            int j = md.length;
            char str1[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str1[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str1[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str1);

        } catch (Exception e) {
            return null;
        }
    }
}
