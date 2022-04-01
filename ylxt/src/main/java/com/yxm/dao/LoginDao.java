package com.yxm.dao;

import com.yxm.po.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {
    SysUser selectUserByName(String userName);
    String selectPositionName(Integer positionId);
}
