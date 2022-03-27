package com.yxm.service;

import com.yxm.vo.LayuiTableData;
import com.yxm.vo.OrderAndCombo;

public interface ManagementService {
    LayuiTableData<OrderAndCombo> selectOrderAll(Integer page, Integer limit,String elderName);
}
