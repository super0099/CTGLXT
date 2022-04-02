package com.yxm.po;

import java.io.Serializable;

public class ShoppingCar implements Serializable {
    private int Id;
    private Integer userId;

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

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "Id=" + Id +
                ", userId=" + userId +
                '}';
    }
}
