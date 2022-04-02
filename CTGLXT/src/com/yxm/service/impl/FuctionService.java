package com.yxm.service.impl;

import com.yxm.dao.IFuctionDao;
import com.yxm.dao.impl.FuctionDao;
import com.yxm.po.dbUser;
import com.yxm.service.IFuctionService;

public class FuctionService implements IFuctionService {
    private static IFuctionDao fuctionDao = new FuctionDao();
    @Override
    public boolean alteruser(dbUser dbuser) {
        return this.fuctionDao.alteruser(dbuser);
    }

    @Override
    public dbUser selectUser(Integer userId) {
        return this.fuctionDao.selectUser(userId);
    }
}
