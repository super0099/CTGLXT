package com.yxm.service;

import com.yxm.po.SysOut;
import com.yxm.vo.LayuiTableData;
import java.util.Date;

public interface OutService {
    LayuiTableData<SysOut> selectOutAll(Integer page, Integer limit, String elderName, Date outTimes);
    boolean insert(SysOut sysOut);
    SysOut selectById(Integer outId);
    boolean update(SysOut sysOut);
    boolean deleteById(Integer outId);
}
