package com.yxm.dao;

import com.yxm.po.SysAuthorize;
import com.yxm.po.SysPosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JurisdictionDao {
    Integer selectCount();
    List<SysPosition> selectPageList(@Param("page") Integer page, @Param("limit") Integer limit, @Param("positionName") String positionName);
    SysPosition selectById(Integer positionId);
    Integer insert(SysPosition sysPosition);
    Integer update(SysPosition sysPosition);
    Integer deleteById(Integer positionId);
    Integer selectPositionUse(Integer positionId);
    Integer deleteAuthorize(Integer positionId);
    List<Integer> selectMenuIdByPositionId(Integer positionId);
    Integer inserts(SysAuthorize sysAuthorize);
    Integer deleteByIds(@Param("roleId") int roleId,@Param("menuId") int menuId);
}
