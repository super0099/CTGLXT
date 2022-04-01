package com.yxm.service;

import com.yxm.po.SysBerth;
import com.yxm.vo.BerthAndElder;
import com.yxm.vo.H5SelectVo;

import java.util.List;

public interface LocationService {
    List<BerthAndElder> selectBerthAll(Integer berthId);
    List<H5SelectVo> selectBerthGrade(Integer grade);
    List<H5SelectVo> selectNurseTypeBerth();
    SysBerth selectBerthById(Integer berthId);

}
