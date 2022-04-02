package com.yxm.po;

import java.io.Serializable;

public class dbMenutype implements Serializable {
    private static final long serialVersionUID = -1229293568705470562L;
    private int Id;
    private String menuType;

    public int getId() {
        return Id;
    }

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
        return "dbMenutype{" +
                "Id=" + Id +
                ", menuType='" + menuType + '\'' +
                '}';
    }
}
