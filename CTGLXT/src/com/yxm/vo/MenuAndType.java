package com.yxm.vo;

import com.yxm.po.dbMenu;

public class MenuAndType extends dbMenu {
    private static final long serialVersionUID = 2737229327384811751L;
    private int Id;
    private String menuType;

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public void setId(int id) {
        Id = id;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    @Override
    public String toString() {
        return "MenuAndType{" +
                "Id=" + Id +
                ", menuType='" + menuType + '\'' +
                '}';
    }
}
