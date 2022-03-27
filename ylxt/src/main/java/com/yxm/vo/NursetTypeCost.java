package com.yxm.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class NursetTypeCost implements Serializable {
    private BigDecimal alimony;
    private BigDecimal berth;
    private BigDecimal boardWages;
    private BigDecimal serviceCharge;

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
        return "NursetTypeCost{" +
                "alimony=" + alimony +
                ", berth=" + berth +
                ", boardWages=" + boardWages +
                ", serviceCharge=" + serviceCharge +
                '}';
    }
}
