package com.yxm.service.impl;

import com.yxm.dao.OrderDao;
import com.yxm.service.ManagementService;
import com.yxm.vo.LayuiTableData;
import com.yxm.vo.OrderAndCombo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManagementServiceImpl implements ManagementService {
    @Autowired
    private OrderDao orderDao;
    @Override
    public LayuiTableData<OrderAndCombo> selectOrderAll(Integer page, Integer limit,String elderName) {
        Integer count = this.orderDao.selectOrderCount();
        List<OrderAndCombo> data = this.orderDao.selectOrderAll(page,limit,elderName);
        return new LayuiTableData<>(count,data);
    }
}
