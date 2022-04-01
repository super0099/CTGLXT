package com.yxm.vo;

import com.yxm.po.SysOrder;

import java.io.Serializable;

public class OrderAndCombo extends SysOrder implements Serializable {
    private String bf;
    private String lc;
    private String de;

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    @Override
    public String toString() {
        return "OrderAndCombo{" +
                "bf='" + bf + '\'' +
                ", lc='" + lc + '\'' +
                ", de='" + de + '\'' +
                '}';
    }
}
