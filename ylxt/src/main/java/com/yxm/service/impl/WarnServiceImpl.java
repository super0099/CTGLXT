package com.yxm.service.impl;

import com.yxm.dao.WarnDao;
import com.yxm.po.SysWarn;
import com.yxm.service.WarnService;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarnServiceImpl implements WarnService {
    @Autowired
    private WarnDao warnDao;
    @Override
    public LayuiTableData<SysWarn> selectWarnAll(Integer page, Integer limit, String elderName, String idCode) {
        Integer count = this.warnDao.selectWarnCount();
        List<SysWarn> data = this.warnDao.selectWarnAll(page,limit,elderName,idCode);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public boolean Insert(SysWarn sysWarn) {
        return this.warnDao.insert(sysWarn)>0;
    }

    @Override
    public boolean update(SysWarn sysWarn) {
        return this.warnDao.update(sysWarn)>0;
    }

    @Override
    public boolean delete(Integer warnId) {
        return this.warnDao.delete(warnId)>0;
    }

    @Override
    public SysWarn selectById(Integer warnId) {
        return this.warnDao.selectById(warnId);
    }
}
