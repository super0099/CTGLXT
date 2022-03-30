package com.example.myfirst.parnterFrag;

import java.io.Serializable;

public class ParnterAnalysisBean {

    /**
     * reason : success
     * result : {"men":"白羊","women":"双子","zhishu":"90","bizhong":"44:50","xiangyue":"100","tcdj":"80","jieguo":"相濡以沫的一对 ","lianai":"白羊座属火象，双子座属风象，两者都是同属阳性星座，加上个性也很相近，大家都是乐观一族。由于两个人的生活态度和方式接近，双子和白羊结合更添趣味。白羊有着不死守原则，喜欢求变求新的特性，很迎合双子的口味，两者在一起能激发出新东西。他们的生活和爱情不会乏味，事业上也能携手并进，所以这一对组合在一起会永葆青春。","zhuyi":"白羊座个性急，脾气坏，双子座喜欢自由。在生活上白羊座男生如果能尊重双子座女生的内心和自由，双子座女生也能体谅白羊座男生的坏脾气，会这样就会少很多冲突。双子座男生是那种行动迅速、脑筋好，个性温顺的类型，和白羊座比是属强势，但白羊座个性也很好强，绝不会甘于落后，所以要注意这一点，懂得适度谦让羊儿。还有双子座在思考方面不要过度跳跃，羊儿如果一追不上，个性火爆的羊儿就会成为一座活火山，这样就会引火自焚了。"}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean implements Serializable {
        /**
         * men : 白羊
         * women : 双子
         * zhishu : 90
         * bizhong : 44:50
         * xiangyue : 100
         * tcdj : 80
         * jieguo : 相濡以沫的一对
         * lianai : 白羊座属火象，双子座属风象，两者都是同属阳性星座，加上个性也很相近，大家都是乐观一族。由于两个人的生活态度和方式接近，双子和白羊结合更添趣味。白羊有着不死守原则，喜欢求变求新的特性，很迎合双子的口味，两者在一起能激发出新东西。他们的生活和爱情不会乏味，事业上也能携手并进，所以这一对组合在一起会永葆青春。
         * zhuyi : 白羊座个性急，脾气坏，双子座喜欢自由。在生活上白羊座男生如果能尊重双子座女生的内心和自由，双子座女生也能体谅白羊座男生的坏脾气，会这样就会少很多冲突。双子座男生是那种行动迅速、脑筋好，个性温顺的类型，和白羊座比是属强势，但白羊座个性也很好强，绝不会甘于落后，所以要注意这一点，懂得适度谦让羊儿。还有双子座在思考方面不要过度跳跃，羊儿如果一追不上，个性火爆的羊儿就会成为一座活火山，这样就会引火自焚了。
         */

        private String men;
        private String women;
        private String zhishu;
        private String bizhong;
        private String xiangyue;
        private String tcdj;
        private String jieguo;
        private String lianai;
        private String zhuyi;

        public String getMen() {
            return men;
        }

        public void setMen(String men) {
            this.men = men;
        }

        public String getWomen() {
            return women;
        }

        public void setWomen(String women) {
            this.women = women;
        }

        public String getZhishu() {
            return zhishu;
        }

        public void setZhishu(String zhishu) {
            this.zhishu = zhishu;
        }

        public String getBizhong() {
            return bizhong;
        }

        public void setBizhong(String bizhong) {
            this.bizhong = bizhong;
        }

        public String getXiangyue() {
            return xiangyue;
        }

        public void setXiangyue(String xiangyue) {
            this.xiangyue = xiangyue;
        }

        public String getTcdj() {
            return tcdj;
        }

        public void setTcdj(String tcdj) {
            this.tcdj = tcdj;
        }

        public String getJieguo() {
            return jieguo;
        }

        public void setJieguo(String jieguo) {
            this.jieguo = jieguo;
        }

        public String getLianai() {
            return lianai;
        }

        public void setLianai(String lianai) {
            this.lianai = lianai;
        }

        public String getZhuyi() {
            return zhuyi;
        }

        public void setZhuyi(String zhuyi) {
            this.zhuyi = zhuyi;
        }
    }
}
