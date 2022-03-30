package com.example.myfirst.luckFrag;

import java.io.Serializable;
import java.util.List;

public class LuckAnalysisBean {

    /**
     * name : 双子座
     * date : 2021年
     * year : 2021
     * mima : {"info":"稳定自己的内心","text":["双子们一向是喜欢无拘无束、充满好奇心的，在2021年，你们仍然会有许多机会去探索这个未知的世界，改变自己现有的状态。这一年的贵人运不错，你也会得到许多机会来提升自己，然而，土星和冥王星的不良相位会使得双子们在2021年拥有比较强烈的野心，有不达目的不罢休的心态，胜负心也比较强。在这样的心态驱使下，你们需要多花些精力稳定自己的内心，让自己禁得住急功近利的诱惑，这样才能使你的道路走得更顺利。"]}
     * career : ["与11宫交相呼应的数个吉星使你们在工作、学习之中能够得到贵人相助。同时，你们本身就是事业心比较强的人，今年在土星的影响之下，你们更是野心勃勃，甚至经常达到加班加点、废寝忘食的境界。而还在读书的小伙伴们也同样动力满满，对于学习很有劲头。但是，大家在工作学习之余一定不要忘记身边的家人、朋友，多多珍惜眼前人，花些时间与他们共同度过一段幸福温馨的时光。"]
     * love : ["2021年，土星的位置变动会使你们的感情生活产生一些波动。仍然单身的双子座们这一年的桃花并不算特别旺盛，大家在选择进入一段亲密关系时一定要多加谨慎，以避免烂桃花的骚扰。已经有伴的朋友们也需要多花一些时间陪伴对方，经常制造一些幸福感与小确幸，这可以很好地稳固两人之间的关系。双子座今年可佩戴一个星盘保岁吉宏项链作为全年的幸运护身符饰物，银币铸造的船舵星符可提升双子们的能量指数，寓寓意今年信心十足、目标明确、勇往直前！"]
     * health : ["本年你们的健康运势尚可，不过出于火星的影响，你们需要格外关注一下口腔溃疡、扁桃体炎症、麦粒肿、结膜炎等问题。大家可以在平时适当地多摄入一些水分，清热去火。"]
     * finance : ["2021年，土星将会运行在双子座的财富宫，因此你们的正财运在今年是会略微有些波折。虽说你们的收入一直保持在比较平稳的水平，但时不时就会产生几笔比较大额的支出，比如购买交通用具、添置家具、装修房屋、教育投资等等。因此，你需要将自己的开支精打细算一下，避免情绪化消费可以使你的经济状况更加平稳。双子座今年可佩戴一串八芒马蹄守禄手链来提升金钱指数，八芒星和马蹄铁是古希腊代表财富与幸运的象征，寓意添加财气的同时能避免意外的破耗情况，期望今年财运亨通，学业事业皆顺遂。"]
     * luckeyStone : 鹰眼石
     * future : 
     * resultcode : 200
     * error_code : 0
     */

    private String name;
    private String date;
    private int year;
    private MimaBean mima;
    private String luckeyStone;
    private String future;
    private String resultcode;
    private int error_code;
    private List<String> career;
    private List<String> love;
    private List<String> health;
    private List<String> finance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    
    public MimaBean getMima() {
        return mima;
    }

    public void setMima(MimaBean mima) {
        this.mima = mima;
    }

    
    public String getLuckeyStone() {
        return luckeyStone;
    }

    public void setLuckeyStone(String luckeyStone) {
        this.luckeyStone = luckeyStone;
    }

    
    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    
    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    
    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    
    public List<String> getCareer() {
        return career;
    }

    public void setCareer(List<String> career) {
        this.career = career;
    }

    
    public List<String> getLove() {
        return love;
    }

    public void setLove(List<String> love) {
        this.love = love;
    }

    
    public List<String> getHealth() {
        return health;
    }

    public void setHealth(List<String> health) {
        this.health = health;
    }

    
    public List<String> getFinance() {
        return finance;
    }

    public void setFinance(List<String> finance) {
        this.finance = finance;
    }

    public static class MimaBean implements Serializable {
        /**
         * info : 稳定自己的内心
         * text : ["双子们一向是喜欢无拘无束、充满好奇心的，在2021年，你们仍然会有许多机会去探索这个未知的世界，改变自己现有的状态。这一年的贵人运不错，你也会得到许多机会来提升自己，然而，土星和冥王星的不良相位会使得双子们在2021年拥有比较强烈的野心，有不达目的不罢休的心态，胜负心也比较强。在这样的心态驱使下，你们需要多花些精力稳定自己的内心，让自己禁得住急功近利的诱惑，这样才能使你的道路走得更顺利。"]
         */

        private String info;
        private List<String> text;

        
        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        
        public List<String> getText() {
            return text;
        }

        public void setText(List<String> text) {
            this.text = text;
        }
    }
}
