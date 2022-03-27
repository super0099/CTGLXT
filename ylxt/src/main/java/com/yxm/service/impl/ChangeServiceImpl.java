package com.yxm.service.impl;

import com.yxm.dao.ChangeDao;
import com.yxm.service.ChangeService;
import com.yxm.vo.ElderAndAlteration;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeServiceImpl implements ChangeService {
    @Autowired
    private ChangeDao changeDao;
    @Override
    public LayuiTableData<ElderAndAlteration> selectAlterationAll(Integer page, Integer limit, String elderName, String idNumber) {
        Integer count = this.changeDao.selectAlterationCount();
        List<ElderAndAlteration> selectAlteration = this.changeDao.selectAlterationAll(page,limit,elderName,idNumber);
        return new LayuiTableData<>(count,selectAlteration);
    }
}
