package com.yxm.service;

import com.yxm.po.SysOrder;

import java.util.Date;

public interface OrderService {
    SysOrder selectOrderByElderIdAndDate(Integer elderId,Date orderDate);
    boolean insertOrder(SysOrder sysOrder);
}
