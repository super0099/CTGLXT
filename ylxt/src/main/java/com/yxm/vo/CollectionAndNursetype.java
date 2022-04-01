package com.yxm.vo;

import com.yxm.po.SysCollection;

import java.math.BigDecimal;

public class CollectionAndNursetype extends SysCollection {
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
        return "CollectionAndNursetype{" +
                "alimony=" + alimony +
                ", berth=" + berth +
                ", boardWages=" + boardWages +
                ", serviceCharge=" + serviceCharge +
                '}';
    }
}
