package com.yxm.util.ValidateImage;

import com.yxm.util.ValidateImage.util.Randoms;

import java.awt.*;

/**
 * <p>生成属性运算 Gif验证码类</p>
 * @author sxj
 * @version 0.1
 */
public class MathGifCaptcha extends GifCaptcha {
    public MathGifCaptcha() {
    }

    public MathGifCaptcha(int width, int height) {
        super(width, height);
    }

    public MathGifCaptcha(int width, int height, Font font) {
        this(width,height);
        this.font=font;
    }

    //重写随机字符串生成类
    @Override
    protected char[] alphas() {
        String[] rString= Randoms.randomMathOperations();
        String showString=rString[0];//取出需要显示的字符串
        this.keyString=rString[1];//取出结果
        //返回需要显示的char数组
        this.len=showString.length();//更新字符长度
        return showString.toCharArray();
    }
}
