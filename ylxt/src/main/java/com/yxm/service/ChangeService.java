package com.yxm.service;

import com.yxm.vo.ElderAndAlteration;
import com.yxm.vo.LayuiTableData;

public interface ChangeService {
    LayuiTableData<ElderAndAlteration> selectAlterationAll(Integer page,Integer limit,String elderName,String idNumber);
}
