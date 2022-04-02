package com.yxm.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class dbDiiscounts implements Serializable {
    private static final long serialVersionUID = -7063332898788841740L;
    private int Id;
    private String discountsName;
    private BigDecimal discount;
    private Integer userId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDiscountsName() {
        return discountsName;
    }

    public void setDiscountsName(String discountsName) {
        this.discountsName = discountsName;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "dbDiiscounts{" +
                "Id=" + Id +
                ", discountsName='" + discountsName + '\'' +
                ", discount=" + discount +
                ", userId=" + userId +
                '}';
    }
}
