package com.example.myfirst.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.myfirst.bean.StarBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取Assets文件夹的工具类
 */

public class AssetsUtils {
    private static Map<String,Bitmap>logoImgMap;
    private static Map<String,Bitmap>contentLogoImgMap;
    /*读取文件夹当中的内容,存放到字符串当中*/
    public static String getJsonFromAssets(Context context,String filename){
        //1.获取Asstse文件夹管理器
        AssetManager am = context.getResources().getAssets();
        //创建输出流,作用是把读取的文件输出出去
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //2.获取输入流
        try {
            //作用把文件读取到,读到缓冲区中
            InputStream inputStream = am.open(filename);
            //读取内容存放到内存流当中
            int hasRead = 0;
            byte[] buf = new byte[1024];
            while (true){
                hasRead = inputStream.read(buf);
                if(hasRead==-1){
                    break;
                }else {
                    baos.write(buf,0,hasRead);
                }
            }
            String msg = baos.toString();
            inputStream.close();
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*读取Assets文件夹下的图片,返回Bitmap对象*/
    public static Bitmap getBitmapFromAssets(Context context,String filename){
        Bitmap bitmap = null;
        //1.获取Asstse文件夹管理器
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open(filename);
//            通过绘图管理器,将输入流转换成绘图对象
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * 将Assets文件夹当中的图片一起读取,放置在内存当中,便于管理
     */
    public static void saveBitmapAssets(Context context, StarBean starInfoBean){
        logoImgMap = new HashMap<>();
        contentLogoImgMap = new HashMap<>();
        List<StarBean.StarinfoBean> starinfoBeans = starInfoBean.getStarinfo();
        //获取到列表中的logoName
        for(int i =0;i<starinfoBeans.size();i++){
            String logoName=starinfoBeans.get(i).getLogoname();
            String fileName = "xzlogo/"+logoName+".png";
            Bitmap logoBm = getBitmapFromAssets(context,fileName);
            logoImgMap.put(logoName,logoBm);

            String contentName = "xzcontentlogo/"+logoName+".png";
            Bitmap bitmap = getBitmapFromAssets(context,contentName);
            contentLogoImgMap.put(logoName,bitmap);
        }
    }
    public static Map<String,Bitmap> getLogoImgMap(){
        return logoImgMap;
    }
    public static Map<String,Bitmap> getContentLogoImgMap(){
        return contentLogoImgMap;
    }
}
