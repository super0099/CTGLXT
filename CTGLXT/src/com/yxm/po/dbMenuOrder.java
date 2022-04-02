package com.yxm.po;

import java.io.Serializable;

public class dbMenuOrder implements Serializable {
    private static final long serialVersionUID = 1005790129023066042L;
    private int Id;
    private Integer orderFormId;
    private Integer nemuId;
    private Integer orderType;
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Integer getOrderFormId() {
        return orderFormId;
    }

    public void setOrderFormId(Integer orderFormId) {
        this.orderFormId = orderFormId;
    }

    public Integer getNemuId() {
        return nemuId;
    }

    public void setNemuId(Integer nemuId) {
        this.nemuId = nemuId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "dbMenuOrder{" +
                "Id=" + Id +
                ", orderFormId=" + orderFormId +
                ", nemuId=" + nemuId +
                ", orderType=" + orderType +
                ", quantity=" + quantity +
                '}';
    }
}
