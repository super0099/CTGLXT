package com.yxm.po;

import java.io.Serializable;

public class dbCollect implements Serializable {
    private static final long serialVersionUID = -7764016219088906778L;
    private int Id;
    private Integer menuId;
    private Integer userId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "dbCollect{" +
                "Id=" + Id +
                ", menuId=" + menuId +
                ", userId=" + userId +
                '}';
    }
}
