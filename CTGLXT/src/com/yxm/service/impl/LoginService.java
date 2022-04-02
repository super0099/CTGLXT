package com.yxm.service.impl;

import com.yxm.dao.ILoginDao;
import com.yxm.dao.impl.LoginDao;
import com.yxm.po.dbUser;
import com.yxm.service.ILoginService;

public class LoginService implements ILoginService {
    private static ILoginDao loginDao = new LoginDao();
    @Override
    public dbUser selectUser(String userName) {
        return this.loginDao.selectUser(userName);
    }

    @Override
    public int selectCountPhone(String phone) {
        return this.loginDao.selectCountPhone(phone);
    }

    @Override
    public boolean inserUser(dbUser dbuser) {
        return this.loginDao.insertUser(dbuser);
    }

    @Override
    public dbUser alteruser(dbUser dbuser) {
        return null;
    }


}
