package com.yxm.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class dbWallet implements Serializable {
    private static final long serialVersionUID = 8604762706986527571L;
    //Id  userId  balance
    private int Id;
    private Integer userId;
    private BigDecimal balance;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    @Override
    public String toString() {
        return "dbWallet{" +
                "Id=" + Id +
                ", userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}
