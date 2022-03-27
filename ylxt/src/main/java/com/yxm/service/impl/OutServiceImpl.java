package com.yxm.service.impl;

import com.yxm.dao.OutDao;
import com.yxm.po.SysOut;
import com.yxm.service.OutService;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class OutServiceImpl implements OutService {
    @Autowired
    private OutDao outDao;
    @Override
    public LayuiTableData<SysOut> selectOutAll(Integer page, Integer limit, String elderName, Date outTimes) {
        Integer count = this.outDao.selectOutCount();
        List<SysOut> data = this.outDao.selectOutAll(page,limit,elderName,outTimes);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public boolean insert(SysOut sysOut) {
        return this.outDao.insert(sysOut)>0;
    }

    @Override
    public SysOut selectById(Integer outId) {
        return this.outDao.selectById(outId);
    }

    @Override
    public boolean update(SysOut sysOut) {
        return this.outDao.update(sysOut)>0;
    }

    @Override
    public boolean deleteById(Integer outId) {
        return this.outDao.deleteById(outId)>0;
    }
}
