package com.yxm.service;

import com.yxm.po.dbSite;

import java.util.List;

public interface ISiteService {
    List<dbSite> selectSite(Integer userId);
    dbSite selectSiteData(Integer siteId);
    boolean addSite(dbSite dbSite);
    boolean compileSite(dbSite dbSite,Integer siteId);
    boolean deleteSite(Integer siteId);
}
