package com.yxm.vo;

import com.yxm.po.SysBerth;

public class BerthAndElder extends SysBerth {
    private String elderName;
    private Byte elderGender;

    public Byte getElderGender() {
        return elderGender;
    }

    public void setElderGender(Byte elderGender) {
        this.elderGender = elderGender;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    @Override
    public String toString() {
        return "BerthAndElder{" +
                "elderName='" + elderName + '\'' +
                ", elderGender=" + elderGender +
                '}';
    }
}
