package com.yxm.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SysCheckout implements Serializable {
    private Integer id;
    private Integer elderId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expire;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date practicalExpire;
    private BigDecimal amount;
    private BigDecimal needRefund;
    private String elderName;
    private String elderIdCode;
    private Byte state;

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

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Date getPracticalExpire() {
        return practicalExpire;
    }

    public void setPracticalExpire(Date practicalExpire) {
        this.practicalExpire = practicalExpire;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getNeedRefund() {
        return needRefund;
    }

    public void setNeedRefund(BigDecimal needRefund) {
        this.needRefund = needRefund;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getElderIdCode() {
        return elderIdCode;
    }

    public void setElderIdCode(String elderIdCode) {
        this.elderIdCode = elderIdCode;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SysCheckout{" +
                "id=" + id +
                ", elderId=" + elderId +
                ", expire=" + expire +
                ", practicalExpire=" + practicalExpire +
                ", amount=" + amount +
                ", needRefund=" + needRefund +
                ", elderName='" + elderName + '\'' +
                ", elderIdCode='" + elderIdCode + '\'' +
                ", state=" + state +
                '}';
    }
}
