package com.yxm.service;

import com.yxm.po.SysNursetype;
import com.yxm.vo.LayuiTableData;

public interface MaintainService {
    LayuiTableData<SysNursetype> selectNurseTypeAll(Integer page,Integer limit);
    boolean insert(SysNursetype sysNursetype);
    boolean update(SysNursetype sysNursetype);
    boolean delete(Integer id);
    SysNursetype selectUserById(Integer id);
}
