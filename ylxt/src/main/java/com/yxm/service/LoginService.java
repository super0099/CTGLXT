package com.yxm.service;

import com.yxm.po.SysUser;

public interface LoginService {
    SysUser selectUserByName(String userName);
    String selectPositionName(Integer positionId);
}
