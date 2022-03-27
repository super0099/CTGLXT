package com.yxm.vo;

import com.yxm.po.SysElder;

public class ElderAndBerth extends SysElder {
    private String berthNumber;

    public String getBerthNumber() {
        return berthNumber;
    }

    public void setBerthNumber(String berthNumber) {
        this.berthNumber = berthNumber;
    }

    @Override
    public String toString() {
        return "ElderAndBerth{" +
                "berthNumber='" + berthNumber + '\'' +
                '}';
    }
}
