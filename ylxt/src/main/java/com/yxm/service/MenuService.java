package com.yxm.service;

import com.yxm.po.SysMenu;
import com.yxm.vo.MenuTableTreeVo;

import java.util.List;

public interface MenuService {
    List<MenuTableTreeVo> selectMenuByPositionId(Integer positionId);
}
