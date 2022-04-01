package com.yxm.po;

import java.io.Serializable;

public class SysAuthorize implements Serializable {
    //权限表字段
    private Integer id;
    private Integer positionId;
    private Integer menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "SysAuthorize{" +
                "id=" + id +
                ", positionId=" + positionId +
                ", menuId=" + menuId +
                '}';
    }
}
