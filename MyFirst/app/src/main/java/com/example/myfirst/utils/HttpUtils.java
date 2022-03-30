package com.example.myfirst.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static String getJsonFromNet(String path){
        String json = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //1.将路径转成url对象
            URL url = new URL(path);
            //2.获取网络连接对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //3.开始连接
            conn.connect();
            //读取输入流当中的内容
            InputStream is = conn.getInputStream();
            int hasRed = 0;
            byte[] buf = new byte[1024];
            while (true){
                hasRed = is.read(buf);
                if(hasRed==-1){
                    break;
                }else {
                    baos.write(buf,0,hasRed);
                }
            }
            is.close();
            json = baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
