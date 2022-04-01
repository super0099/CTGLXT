package com.yxm.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class SysNursetype implements Serializable {
    //护理类型表字段
    //id,nurseType_name,alimony,berth,board_wages,service_charge
    private Integer id;
    private String nurseType;
    private BigDecimal alimony;
    private BigDecimal berth;
    private BigDecimal boardWages;
    private BigDecimal serviceCharge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNurseType() {
        return nurseType;
    }

    public void setNurseType(String nurseType) {
        this.nurseType = nurseType;
    }

    public BigDecimal getAlimony() {
        return alimony;
    }

    public void setAlimony(BigDecimal alimony) {
        this.alimony = alimony;
    }

    public BigDecimal getBerth() {
        return berth;
    }

    public void setBerth(BigDecimal berth) {
        this.berth = berth;
    }

    public BigDecimal getBoardWages() {
        return boardWages;
    }

    public void setBoardWages(BigDecimal boardWages) {
        this.boardWages = boardWages;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    @Override
    public String toString() {
        return "SysNursetype{" +
                "id=" + id +
                ", nurseType='" + nurseType + '\'' +
                ", alimony=" + alimony +
                ", berth=" + berth +
                ", boardWages=" + boardWages +
                ", serviceCharge=" + serviceCharge +
                '}';
    }
}
