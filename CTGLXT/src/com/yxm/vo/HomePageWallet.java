package com.yxm.vo;

import com.yxm.po.dbUser;

import java.math.BigDecimal;

public class HomePageWallet extends dbUser {
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "HomePageWallet{" +
                "balance=" + balance +
                '}';
    }
}
