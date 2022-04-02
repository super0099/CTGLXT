package com.yxm.vo;

import com.yxm.po.dbMenuOrder;

public class OrderMenu extends dbMenuOrder {
    private static final long serialVersionUID = 5856757675921315224L;
    private String picture;
    private String menuName;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "OrderMenu{" +
                "picture='" + picture + '\'' +
                ", menuName='" + menuName + '\'' +
                '}';
    }
}
