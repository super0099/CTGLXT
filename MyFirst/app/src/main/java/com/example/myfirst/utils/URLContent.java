package com.example.myfirst.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLContent {
    //配对网络接口
    public static String getParnterUrl(String man,String woman){
        String mans = man.replace("座","");
        String womans = woman.replace("座","");
        try {
            mans = URLEncoder.encode(mans,"UTF-8");
            womans = URLEncoder.encode(womans,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://apis.juhe.cn/xzpd/query?men="+mans+"&women="+womans+"&key=aab7f23b9a6149ef03e1b8136e38b640";
        return url;
    }
    //星座运势接口
    public static String getLuckUrl(String name){
        try {
            name = URLEncoder.encode(name,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String Url = "http://web.juhe.cn:8080/constellation/getAll?consName="+name+"&type=year&key=37654c72ae968cedc1f7f173181eb019";
        return Url;
    }
}
