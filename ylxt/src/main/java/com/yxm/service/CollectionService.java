package com.yxm.service;

import com.yxm.po.SysCollection;
import com.yxm.vo.CollectionAndNursetype;
import com.yxm.vo.LayuiTableData;

import java.util.Date;

public interface CollectionService {
    SysCollection selectElderOrder(Integer elderId,String years);
    LayuiTableData<CollectionAndNursetype> selectElderPayment(Integer elderId);
    LayuiTableData<CollectionAndNursetype> selectElderAllSettle(Integer elderId);
    LayuiTableData<CollectionAndNursetype> selectArrearageElder(Integer elderId);
}
