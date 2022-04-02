package com.yxm.service;

import com.yxm.po.dbMenu;

import java.util.List;

public interface IParticularService {
    dbMenu selectMenu(Integer menuId);
    List<dbMenu> ranking();
    int myCollectMenu(Integer userId,Integer menuId);
    boolean addCollect(Integer userId,Integer menuId);
}
