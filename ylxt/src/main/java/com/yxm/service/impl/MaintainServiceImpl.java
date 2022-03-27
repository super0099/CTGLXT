package com.yxm.service.impl;

import com.yxm.dao.MaintainDao;
import com.yxm.po.SysNursetype;
import com.yxm.service.MaintainService;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintainServiceImpl implements MaintainService {
    @Autowired
    private MaintainDao maintainDao;

    @Override
    public LayuiTableData<SysNursetype> selectNurseTypeAll(Integer page, Integer limit) {
        Integer count = this.maintainDao.selectNurseTypeCount();
        List<SysNursetype> data = this.maintainDao.selectNurseTypeAll(page,limit);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public boolean insert(SysNursetype sysNursetype) {
        return this.maintainDao.insert(sysNursetype)>0;
    }

    @Override
    public boolean update(SysNursetype sysNursetype) {
        return this.maintainDao.update(sysNursetype)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return this.maintainDao.delete(id)>0;
    }

    @Override
    public SysNursetype selectUserById(Integer id) {
        return this.maintainDao.selectUserById(id);
    }
}
