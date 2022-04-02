package com.yxm.service.impl;

import com.yxm.dao.ISiteDao;
import com.yxm.dao.impl.SiteDao;
import com.yxm.po.dbSite;
import com.yxm.service.ISiteService;

import java.util.List;

public class SiteService implements ISiteService {
    private static ISiteDao siteDao = new SiteDao();
    @Override
    public List<dbSite> selectSite(Integer userId) {
        return this.siteDao.selectSite(userId);
    }

    @Override
    public dbSite selectSiteData(Integer siteId) {
        return this.siteDao.selectSiteData(siteId);
    }

    @Override
    public boolean addSite(dbSite dbSite) {
        return this.siteDao.addSite(dbSite);
    }

    @Override
    public boolean compileSite(dbSite dbSite, Integer siteId) {
        return this.siteDao.compileSite(dbSite,siteId);
    }

    @Override
    public boolean deleteSite(Integer siteId) {
        return this.siteDao.deleteSite(siteId);
    }
}
