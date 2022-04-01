package com.yxm.vo;

import com.yxm.po.SysElder;

import java.util.Date;

public class ElderAndAlteration extends SysElder {
    private Byte alterationType;
    private Date alterationTime;

    public Byte getAlterationType() {
        return alterationType;
    }

    public void setAlterationType(Byte alterationType) {
        this.alterationType = alterationType;
    }

    public Date getAlterationTime() {
        return alterationTime;
    }

    public void setAlterationTime(Date alterationTime) {
        this.alterationTime = alterationTime;
    }

    @Override
    public String toString() {
        return "ElderAndAlteration{" +
                "alterationType=" + alterationType +
                ", alterationTime=" + alterationTime +
                '}';
    }
}
