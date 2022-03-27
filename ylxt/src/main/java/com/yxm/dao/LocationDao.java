package com.yxm.dao;

import com.yxm.po.SysBerth;
import com.yxm.po.SysNursetype;
import com.yxm.vo.BerthAndElder;
import com.yxm.vo.BerthGrade;
import org.springframework.stereotype.Repository;

import java.util.List;
//入住地点信息修改
@Repository
public interface LocationDao {
    List<BerthAndElder> selectBerthAll(Integer berthId);
    List<BerthGrade> selectBerthGrade(Integer grade);
    List<SysNursetype> selectNurseTypeBerth();
    Integer updateBerth(Integer berthId);
    Integer updateBerths(Integer berthId);
    SysBerth selectBerthById(Integer berthId);
}
