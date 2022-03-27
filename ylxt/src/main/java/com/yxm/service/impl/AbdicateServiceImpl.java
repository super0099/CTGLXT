package com.yxm.service.impl;

import com.yxm.dao.CheckoutDao;
import com.yxm.po.SysCheckout;
import com.yxm.service.AbdicateService;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AbdicateServiceImpl implements AbdicateService {
    @Autowired
    private CheckoutDao checkoutDao;
    @Override
    public LayuiTableData<SysCheckout> selectCheckoutAll(Integer page, Integer limit) {
        Integer count = this.checkoutDao.selectCheckoutCount();
        List<SysCheckout> data = this.checkoutDao.selectCheckoutAll(page,limit);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public SysCheckout selectCheckOutById(Integer checkOutId) {
        return this.checkoutDao.selectCheckOutById(checkOutId);
    }

    @Override
    public boolean updateCheckOutById(SysCheckout sysCheckout) {
        return this.checkoutDao.updateCheckOutById(sysCheckout)>0;
    }
}
