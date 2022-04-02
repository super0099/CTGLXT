package com.yxm.util.ValidateImage;

import com.yxm.util.ValidateImage.util.GifEncoder;
import com.yxm.util.ValidateImage.util.Streams;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import static com.yxm.util.ValidateImage.util.Randoms.alpha;
import static com.yxm.util.ValidateImage.util.Randoms.num;

/**
 * <p>Gif验证码类</p>
 *
 * @author wuhongjun
 * @version 1.1
 */
public class GifCaptcha
{
	protected Font font = new Font("Verdana", Font.ITALIC|Font.BOLD, 28);   // 字体
    protected int len = 5;  // 验证码随机字符长度
    protected int width = 150;  // 验证码显示跨度
    protected int height = 40;  // 验证码显示高度
    protected String keyString = null;  // 随机字符串
	
    public GifCaptcha()
    {
    }
    
    /**
     * 生成gif验证码
     * @param width 宽
     * @param height 高
     */
    public GifCaptcha(int width,int height){
        this.width = width;
        this.height = height;
    }
    
    /**
     * 生成gif验证码
     * @param width 宽
     * @param height 高
     * @param len 长度
     */
    public GifCaptcha(int width,int height,int len){
        this(width,height);
        this.len = len;
    }
    
    /**
     * 生成gif验证码
     * @param width 宽
     * @param height 高
     * @param len 长度
     * @param font 字体
     */
    public GifCaptcha(int width,int height,int len,Font font)
    {
        this(width,height,len);
        this.font = font;
    }
 
    
    /**
     * 生成随机字符数组
     * @return 字符数组
     */
    protected char[] alphas()
    {
        char[] cs = new char[len];
        for(int i = 0;i<len;i++)
        {
            cs[i] = alpha();
        }
        keyString = new String(cs);
        return cs;
    }    
    
    
 
    /**
     * 画随机码图
     * @param fontcolor 随机字体颜色
     * @param strs 字符数组
     * @param flag 透明度使用
     * @return BufferedImage
     */
    private BufferedImage graphicsImage(Color[] fontcolor,char[] strs,int flag)
    {
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        //或得图形上下文
        //Graphics2D g2d=image.createGraphics();
        Graphics2D g2d = (Graphics2D)image.getGraphics();
        //利用指定颜色填充背景
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        AlphaComposite ac3;
        int h  = height - ((height - font.getSize()) >>1) ;
        int w = width/len;
        g2d.setFont(font);
        for(int i=0;i<len;i++)
        {
        	int degree = num(24);//旋转度数 最好小于45度
            if (i % 2 == 1)
                degree = degree * (-1);        	
            ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha(flag, i));
            g2d.setComposite(ac3);
            g2d.setColor(fontcolor[i]);
            g2d.drawOval(num(width), num(height), 5+num(10), 5+num(10));            
            g2d.rotate(Math.toRadians(degree), width/2, height/2);//将画布旋转
            g2d.drawString(strs[i]+"", (width-(len-i)*w)+(w-font.getSize())+1, h-4);
            g2d.rotate(-Math.toRadians(degree),  width/2, height/2);//旋转之后，必须旋转回来
            g2d.drawOval(num(width), num(height), 5+num(10), 5+num(10));
        }
        g2d.dispose();
        return image;
    }
 
    /**
     * 获取透明度,从0到1,自动计算步长
     * @return float 透明度
     */
    private float getAlpha(int i,int j)
    {
        int num = i+j;
        float r = (float)1/len,s = (len+1) * r;
        return num > len ? (num *r - s) : num * r;
    }
 
    public String out(OutputStream os)
    {
        try
        {
            GifEncoder gifEncoder = new GifEncoder();   // gif编码类，这个利用了洋人写的编码类，所有类都在附件中
            //生成字符
            gifEncoder.start(os);
            gifEncoder.setQuality(180);
            gifEncoder.setDelay(100);
            gifEncoder.setRepeat(0);
            BufferedImage frame;
            char[] rands =alphas();
            Color fontcolor[]=new Color[len];
            for(int i=0;i<len;i++)
            {
                fontcolor[i]=new Color(20 + num(110), 20 + num(110), 20 + num(110));
            }
            for(int i=0;i<len;i++)
            {
                frame=graphicsImage(fontcolor, rands, i);
                gifEncoder.addFrame(frame);
                frame.flush();
            }
            gifEncoder.finish();
        }finally
        {
            Streams.close(os);
        } 
        return keyString;
    }
}