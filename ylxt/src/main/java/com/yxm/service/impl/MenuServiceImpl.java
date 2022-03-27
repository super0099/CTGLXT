package com.yxm.service.impl;

import com.yxm.dao.MenuDao;
import com.yxm.po.SysMenu;
import com.yxm.service.MenuService;
import com.yxm.vo.MenuTableTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public List<MenuTableTreeVo> selectMenuByPositionId(Integer positionId) {
        List<SysMenu> data = this.menuDao.selectMenu(positionId);
        return this.dealMenuTableTreeVoList(data,0);
    }


    private List<MenuTableTreeVo> dealMenuTableTreeVoList(List<SysMenu> listSouree,int pid){
        List<MenuTableTreeVo> menuList = new ArrayList<>();
        MenuTableTreeVo menuTableTreeVo = null;
        for (SysMenu obj:listSouree) {
            if(obj.getParentId()==pid){
                menuTableTreeVo = new MenuTableTreeVo();
                menuTableTreeVo.setId(obj.getId());
                menuTableTreeVo.setParentId(obj.getParentId());
                menuTableTreeVo.setMenuName(obj.getMenuName());
                menuTableTreeVo.setMenuIcon(obj.getMenuIcon());
                menuTableTreeVo.setAuthorize(obj.getAuthorize());
                menuTableTreeVo.setMenuUrl(obj.getMenuUrl());
                menuTableTreeVo.setMenuType(obj.getMenuType());

                if(obj.getMenuType()<3){
                    menuTableTreeVo.setTreeList(dealMenuTableTreeVoList(listSouree,obj.getId()));

                }else {
                    menuTableTreeVo.setTreeList(null);
                }
                menuList.add(menuTableTreeVo);
            }
        }
        return menuList;
    }
}
