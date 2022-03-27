package com.yxm.dao;

import com.yxm.po.SysCollection;
import com.yxm.po.SysNursetype;
import com.yxm.vo.NursetTypeCost;
import org.springframework.stereotype.Repository;
//护理类型
@Repository
public interface NursetypeDao {
    SysNursetype selectNurseTypeById(Integer id);
    NursetTypeCost selectNursetTypeCost(Integer elderId);
    Integer insertElderOrder(SysCollection sysCollection);
    SysNursetype selectNurseType(Integer id);
}
