package com.yxm.dao;

import com.yxm.po.dbUser;

public interface ILoginDao {
    dbUser selectUser(String suerName);
    int selectCountPhone(String phone);
    boolean insertUser(dbUser dbuser);
}
