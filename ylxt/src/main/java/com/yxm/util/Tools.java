package com.yxm.util;

import java.nio.charset.StandardCharsets;

/**
 * 工具类
 */
public class Tools {
    /**
     * @param value 被验证字符串
     * @return 如果字符串不为空或者长度不为零返回true
     */
    public static boolean isNotNull(String value) {
        return value != null && !"".equals(value.trim());
    }

    /**
     * ISO编码转换成UTF8编码
     *
     * @param s 字符串
     * @return 转换号的字符串
     */
    public static String ISOtoUTF8(String s) {
        try {
            s = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception ignored) {

        }
        return s;
    }

    /**
     * 是否为数字
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 是否是整数
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isInteger(String str) {
        if (str == null) return false;
        return str.matches("^-?\\d+$");
    }

    /**
     * 获取文件扩展名(带点)
     *
     * @return 文件扩展名
     */
    public static String getFileExt(String filename) {
        int index = filename.lastIndexOf(".");
        if (index == -1) return "";
        return filename.substring(index);
    }

    /**
     * 根据图片名称获取图片 ContentType
     * @param filename 图片文件名
     * @return 图片ContentType
     */
    public static String getImageContentType(String filename) {
        int index = filename.lastIndexOf(".");
        String strExt="jpg";;
        if (index != -1) strExt = filename.substring(index + 1);
        String strContentType="image/jpeg";
        switch (strExt){
            case "jpg":
                strContentType="image/jpeg";
                break;
            case "tiff":
                strContentType="image/tiff";
                break;
            case "gif":
                strContentType="image/gif";
                break;
            case "jfif":
                strContentType="image/jpeg";
                break;
            case "png":
                strContentType="image/png";
                break;
            case "tif":
                strContentType="image/tiff";
                break;
            case "ico":
                strContentType="image/x-icon";
                break;
            case "wbmp":
                strContentType="image/vnd.wap.wbmp";
                break;
            case "fax":
                strContentType="image/fax";
                break;
            case "net":
                strContentType="image/pnetvue";
                break;
        }
        return strContentType;
    }
}
