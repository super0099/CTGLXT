package com.yxm.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SysCollection implements Serializable {
    //id,elderId,totalMoney,practical,clearing,paymentDate,operator,expire,remark,state
    private Integer id;
    private Integer elderId;
    private BigDecimal totalMoney;
    private BigDecimal practical;
    private Date clearing;
    private Date paymentDate;
    private String operator;
    private Date expire;
    private String remark;
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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getPractical() {
        return practical;
    }

    public void setPractical(BigDecimal practical) {
        this.practical = practical;
    }

    public Date getClearing() {
        return clearing;
    }

    public void setClearing(Date clearing) {
        this.clearing = clearing;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SysCollection{" +
                "id=" + id +
                ", elderId=" + elderId +
                ", totalMoney=" + totalMoney +
                ", practical=" + practical +
                ", clearing=" + clearing +
                ", paymentDate=" + paymentDate +
                ", operator='" + operator + '\'' +
                ", expire=" + expire +
                ", remark='" + remark + '\'' +
                ", state=" + state +
                '}';
    }
}
