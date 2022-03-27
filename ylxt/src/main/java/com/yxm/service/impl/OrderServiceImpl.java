package com.yxm.service.impl;

import com.yxm.dao.OrderDao;
import com.yxm.po.SysOrder;
import com.yxm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Override
    public SysOrder selectOrderByElderIdAndDate(Integer elderId, Date orderDate) {
        return this.orderDao.selectOrderByElderIdAndDate(elderId,orderDate);
    }

    @Override
    public boolean insertOrder(SysOrder sysOrder) {
        return this.orderDao.insertOrder(sysOrder)>0;
    }
}
