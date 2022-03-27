package com.yxm.service;

import com.yxm.po.SysCheckout;
import com.yxm.vo.LayuiTableData;

public interface AbdicateService {
    LayuiTableData<SysCheckout> selectCheckoutAll(Integer page,Integer limit);
    SysCheckout selectCheckOutById(Integer checkOutId);
    boolean updateCheckOutById(SysCheckout sysCheckout);
}
