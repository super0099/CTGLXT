package com.yxm.dao;

import com.yxm.po.SysNursetype;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintainDao {
    List<SysNursetype> selectNurseTypeAll(@Param("page") Integer page,@Param("limit") Integer limit);
    Integer selectNurseTypeCount();
    Integer insert(SysNursetype sysNursetype);
    Integer update(SysNursetype sysNursetype);
    Integer delete(Integer id);
    SysNursetype selectUserById(Integer id);
}
