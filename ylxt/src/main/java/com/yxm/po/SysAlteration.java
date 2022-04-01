package com.yxm.po;

import java.io.Serializable;
import java.util.Date;

public class SysAlteration implements Serializable {
    private Integer id;
    private Integer elderId;
    private Byte alterationType;
    private Date alterationTime;
    private String elderName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getElderId() {
        return elderId;
    }

    public void setElderId(Integer elderId) {
        this.elderId = elderId;
    }

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

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    @Override
    public String toString() {
        return "SysAlteration{" +
                "id=" + id +
                ", elderId=" + elderId +
                ", alterationType=" + alterationType +
                ", alterationTime=" + alterationTime +
                ", elderName='" + elderName + '\'' +
                '}';
    }
}
