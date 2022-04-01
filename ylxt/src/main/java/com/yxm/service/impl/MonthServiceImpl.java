package com.yxm.service.impl;

import com.yxm.dao.MonthDao;
import com.yxm.service.MonthService;
import com.yxm.vo.NursetTypeCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MonthServiceImpl implements MonthService {
    @Autowired
    private MonthDao monthDao;
    @Override
    public NursetTypeCost selectNurseTypeCost(Integer month,Integer year) {
        return this.monthDao.selectNurseTypeCost(month,year);
    }

    @Override
    public BigDecimal selectIncomeCost(Integer month,Integer year) {
        return this.monthDao.selectIncomeCost(month,year);
    }

    @Override
    public Integer selectIncomeCount(Integer month,Integer year) {
        return this.monthDao.selectIncomeCount(month,year);
    }
}
