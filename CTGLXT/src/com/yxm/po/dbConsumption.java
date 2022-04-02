package com.yxm.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class dbConsumption implements Serializable {
    private static final long serialVersionUID = 2904173260255554423L;
    //Id  date                 type    walletId  balance  money
    private int Id;
    private Date establishDate;
    private String ctype;
    private Integer userId;
    private BigDecimal balance;
    private BigDecimal money;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "dbConsumption{" +
                "Id=" + Id +
                ", establishDate=" + establishDate +
                ", ctype='" + ctype + '\'' +
                ", userId=" + userId +
                ", balance=" + balance +
                ", money=" + money +
                '}';
    }
}
