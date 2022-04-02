package com.yxm.util.ValidateImage;

import com.yxm.util.ValidateImage.util.Streams;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import static com.yxm.util.ValidateImage.util.Randoms.alpha;
import static com.yxm.util.ValidateImage.util.Randoms.num;

/**
 * <p>png格式验证码</p>
 *
 * @author wuhongjun
 * @version 1.0
 */
public class PngCaptcha
{
	protected Font font = new Font("Verdana", Font.ITALIC|Font.BOLD, 28);   // 字体
    protected int len = 5;  // 验证码随机字符长度
    protected int width = 150;  // 验证码显示跨度
    protected int height = 40;  // 验证码显示高度
    protected String keyString = null;  // 随机字符串
	
    public PngCaptcha()
    {
    }
    public PngCaptcha(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    public PngCaptcha(int width, int height, int len){
        this(width,height);
        this.len = len;
    }
    public PngCaptcha(int width, int height, int len, Font font){
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
     * 给定范围获得随机颜色
     * @return Color 随机颜色
     */
    protected Color color(int fc, int bc)
    {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + num(bc - fc);
        int g = fc + num(bc - fc);
        int b = fc + num(bc - fc);
        return new Color(r, g, b);
    }
 
    /**
     * 画随机码图
     * @param strs 文本
     * @param out 输出流
     */
    private boolean graphicsImage(char[] strs, OutputStream out){
        boolean ok = false;
        try
        {
            BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D)bi.getGraphics();
            AlphaComposite ac3;
            Color color ;
            int len = strs.length;
            g.setColor(Color.WHITE);
            g.fillRect(0,0,width,height);
            // 随机画干扰的蛋蛋
            for(int i=0;i<15;i++){
                color = color(150, 250);
                g.setColor(color);
                g.drawOval(num(width), num(height), 5+num(10), 5+num(10));// 画蛋蛋，有蛋的生活才精彩
                color = null;
            }
            g.setFont(font);
            int h  = height - ((height - font.getSize()) >>1),
                w = width/len,
                size = w-font.getSize()+1;
            /* 画字符串 */
            for(int i=0;i<len;i++)
            {
                ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);// 指定透明度
                g.setComposite(ac3);
                color = new Color(20 + num(110), 20 + num(110), 20 + num(110));// 对每个字符都用随机颜色
                g.setColor(color);
                g.drawString(strs[i]+"",(width-(len-i)*w)+size, h-4);
                color = null;
                ac3 = null;
            }
            ImageIO.write(bi, "png", out);
            out.flush();
            ok = true;
        }catch (IOException e){
            ok = false;
        }finally
        {
            Streams.close(out);
        }
        return ok;
    }
    

    /**
     * 生成验证码
     * @throws IOException IO异常
     */    
    public String out(OutputStream out){
        graphicsImage(alphas(), out);
        return keyString;
    }
}