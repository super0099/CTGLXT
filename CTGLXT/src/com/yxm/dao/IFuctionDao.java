package com.yxm.dao;

import com.yxm.po.dbUser;

public interface IFuctionDao {
    boolean alteruser(dbUser dbuser);
    dbUser selectUser(Integer userId);


}
