package com.yxm.service;

import com.yxm.po.SysWarn;
import com.yxm.vo.LayuiTableData;

public interface WarnService {
    LayuiTableData<SysWarn> selectWarnAll(Integer page,Integer limit,String elderName,String idCode);

    boolean Insert(SysWarn sysWarn);

    boolean update(SysWarn sysWarn);

    boolean delete(Integer warnId);

    SysWarn selectById(Integer warnId);
}
