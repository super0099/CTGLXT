package com.yxm.vo;

import com.yxm.po.dbCollect;

public class CollectAndMenu extends dbCollect {
    private static final long serialVersionUID = 6828623605104289797L;
    private String menuName;
    private String picture;

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

    @Override
    public String toString() {
        return "CollectAndMenu{" +
                "menuName='" + menuName + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
