package com.yxm.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderMenuList implements Serializable {
    private static final long serialVersionUID = 9086220041108158251L;
    private Integer quantity;
    private String menuName;
    private String picture;
    private BigDecimal price;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderMenuList{" +
                "quantity=" + quantity +
                ", menuName='" + menuName + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                '}';
    }
}
