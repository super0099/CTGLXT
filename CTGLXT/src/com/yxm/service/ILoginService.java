package com.yxm.service;

import com.yxm.po.dbUser;

public interface ILoginService {
    dbUser selectUser(String userName);
    int selectCountPhone(String phone);
    boolean inserUser(dbUser dbuser);
    dbUser alteruser(dbUser dbuser);
}
