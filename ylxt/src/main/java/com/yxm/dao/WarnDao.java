package com.yxm.dao;

import com.yxm.po.SysWarn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarnDao {
    Integer selectWarnCount();

    List<SysWarn> selectWarnAll(@Param("page")Integer page,@Param("limit")Integer limit,
                                @Param("elderName")String elderName,@Param("idCode")String idCode);

    Integer insert(SysWarn sysWarn);

    Integer update(SysWarn sysWarn);

    Integer delete(Integer warnId);

    SysWarn selectById(Integer warnId);
}
