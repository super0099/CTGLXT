package com.yxm.service;

import com.yxm.po.dbUser;

public interface IFuctionService {
    boolean alteruser(dbUser dbuser);
    dbUser selectUser(Integer userId);
}
