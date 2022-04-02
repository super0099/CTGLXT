package com.yxm.service.impl;

import com.yxm.dao.IPreferentialDao;
import com.yxm.dao.impl.PreferentialDao;
import com.yxm.po.dbDiiscounts;
import com.yxm.service.IPreferentialService;

import java.util.List;

public class PreferentialService implements IPreferentialService {
    private static IPreferentialDao preferentialDao=new PreferentialDao();
    @Override
    public int selectPreferentialCount(Integer userId) {
        return this.preferentialDao.selectPreferentialCount(userId);
    }

    @Override
    public List<dbDiiscounts> selectPreferentialList(Integer userId) {
        return this.preferentialDao.selectPreferentialList(userId);
    }
}
