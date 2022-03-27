package com.yxm.po;

import java.io.Serializable;

public class SysMenu implements Serializable {
    //菜单表子段
    //id,parent_id,menu_name,menu_icon,menu_url,authorize,menu_type
    private Integer id;
    private Integer parentId;
    private String menuName;
    private String menuIcon;
    private String menuUrl;
    private String authorize;
    private Integer menuType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", menuName='" + menuName + '\'' +
                ", menuIcon='" + menuIcon + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", authorize='" + authorize + '\'' +
                ", menuType=" + menuType +
                '}';
    }
}
