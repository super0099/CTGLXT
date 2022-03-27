package com.yxm.dao;

import com.yxm.po.SysPosition;
import com.yxm.po.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountDao {
    List<SysUser> selectUserAll(@Param("page") Integer page, @Param("limit")Integer limit);
    Integer selectUserCount();
    List<SysPosition> selectPosition();
    SysUser selectUserById(Integer userId);
    Integer deleteElderById(Integer userId);
    Integer insert(SysUser sysUser);
    Integer update(SysUser sysUser);
    Integer updates(SysUser sysUser);
    Integer delete(Integer userId);
}
