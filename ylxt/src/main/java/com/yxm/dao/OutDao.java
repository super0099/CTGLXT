package com.yxm.dao;

import com.yxm.po.SysOut;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OutDao {
    List<SysOut> selectOutAll(@Param("page") Integer page, @Param("limit") Integer limit,
                              @Param("elderName") String elderName, @Param("outTimes") Date outTimes);
    Integer selectOutCount();

    Integer insert(SysOut sysOut);

    SysOut selectById(Integer outId);

    Integer update(SysOut sysOut);

    Integer deleteById(Integer outId);
}
