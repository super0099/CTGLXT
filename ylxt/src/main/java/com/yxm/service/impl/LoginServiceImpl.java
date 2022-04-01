package com.yxm.service.impl;

import com.yxm.dao.LoginDao;
import com.yxm.po.SysUser;
import com.yxm.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;

    @Override
    public SysUser selectUserByName(String userName) {
        return this.loginDao.selectUserByName(userName);
    }

    @Override
    public String selectPositionName(Integer positionId) {
        return this.loginDao.selectPositionName(positionId);
    }
}
