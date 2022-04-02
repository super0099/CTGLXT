package com.yxm.dao;

import com.yxm.po.dbMenu;

import java.util.List;

public interface IParticularDao {
    dbMenu selectMenu(Integer menuId);
    List<dbMenu> ranking();
    boolean collect(Integer menuId);
    int myCollectMenu(Integer userId,Integer menuId);
    boolean addCollect(Integer userId,Integer menuId);
}
