package com.yxm.service;

import com.yxm.po.SysCollection;
import com.yxm.po.SysElder;
import com.yxm.po.SysIncome;
import com.yxm.po.SysNursetype;
import com.yxm.vo.ElderIndentData;
import com.yxm.vo.IncomeColumnarVo;
import com.yxm.vo.LayuiTableData;
import com.yxm.vo.NursetTypeCost;

import java.util.List;

public interface CostService {
    List<SysElder> selectElder();
    ElderIndentData selectElderData(Integer elderId);
    NursetTypeCost selectNursetTypeCost(Integer elderId);
    boolean insertElderOrder(SysCollection sysCollection);
    SysNursetype selectNurseType(Integer id);
    IncomeColumnarVo selectIncomeColumnarVo(String years);
    LayuiTableData<SysIncome> selectIncomeAll(Integer page, Integer limit);
}
