package com.yxm.util;

import java.security.MessageDigest;

/**
 * MD5 加密工具类
 * @author sxj
 * @version 0.2
 */
public class MD5Util {
    /**
     * 获取字符串的MD5值
     * @param s 被加密字符串
     * @return 加密后的字符串
     */
	public static String getMD5(String s) {
        char[] hexDigits ={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得 MD5 摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0>>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("aaa"));
        System.out.println(MD5Util.getMD5("vvvv"));
    }
}
