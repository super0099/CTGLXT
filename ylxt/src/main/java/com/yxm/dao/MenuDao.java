package com.yxm.dao;

import com.yxm.po.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {
    List<SysMenu> selectMenu(Integer positionId);
    List<SysMenu> selectMenuAll();
    Integer count(@Param("positionId") Integer positionId, @Param("url") String url);
}
