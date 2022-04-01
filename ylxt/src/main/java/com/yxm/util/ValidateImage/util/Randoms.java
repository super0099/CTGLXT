package com.yxm.util.ValidateImage.util;

import java.util.Random;

/**
 * <p>随机工具类</p>
 *
 * @author: wuhongjun
 * @update sxj add 2021年3月11日
 * @version:1.1  sxj add 2021年3月11日
 */
public class Randoms
{
    private static final Random RANDOM = new Random();
    //定义验证码字符.去除了O和I等容易混淆的字母
    public static final char ALPHA[]={'A','B','C','D','E','F','G','H','G','K','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'
            ,'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z','2','3','4','5','6','7','8','9'};

    /**
     * 数学运算符
     * sxj add 2021年3月11日
     */
    private static final char[] OPERATORS=new char[]{'+','-','×','+','-','×'};

    /**
     * 产生两个数之间的随机数
     * @param min 小数
     * @param max 比min大的数
     * @return int 随机数字
     */
    public static int num(int min, int max)
    {
        return min + RANDOM.nextInt(max - min);
    }
 
    /**
     * 产生0--num的随机数,不包括num
     * @param num 数字
     * @return int 随机数字
     */
    public static int num(int num)
    {
        return RANDOM.nextInt(num);
    }
 
    public static char alpha()
    {
        return ALPHA[num(0, ALPHA.length)];
    }

    /**
     * 随机生成 数学运算 +-× （除法不太好控制）
     * sxj add 2021年3月11日
     * @return 数组，数组[0]显示的数学式子，数组[1]数学式子运算结果
     */
    public static String[] randomMathOperations(){
        int a= num(1,10);//第一个数
        int b=num(1,10);//第二个数（可能为除数，避免为0）
        char operator=OPERATORS[Randoms.num(OPERATORS.length)];//运算符
        int result=0;
        switch (operator){
            case '+':
                a=num(100);//
                result=a+b;
                break;
            case '-':
                a=num(100);//
                result=a-b;
                break;
            case '×':
                result=a*b;
                break;
        }
        String showString=String.valueOf(a)+operator+ b +"=";//把
//        System.out.println("验证码：("+showString+")="+result);
        return new String[]{showString,String.valueOf(result)};
    }


}