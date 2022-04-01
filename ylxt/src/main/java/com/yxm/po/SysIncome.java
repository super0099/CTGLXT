package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SysIncome implements Serializable {
    //id  incomeDate           sumMoney  incomeType
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date incomeDate;
    private BigDecimal sumMoney;
    private String incomeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    @Override
    public String toString() {
        return "SysIncome{" +
                "id=" + id +
                ", incomeDate=" + incomeDate +
                ", sumMoney=" + sumMoney +
                ", incomeType='" + incomeType + '\'' +
                '}';
    }
}
