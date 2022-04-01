package com.yxm.vo;

import com.yxm.po.SysMenu;

import java.util.List;

public class MenuTableTreeVo extends SysMenu {
    //子菜单的list集合
    private List<MenuTableTreeVo> treeList;

    public List<MenuTableTreeVo> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<MenuTableTreeVo> treeList) {
        this.treeList = treeList;
    }
}
