package com.yxm.service;

import com.yxm.po.SysAppointment;
import com.yxm.po.SysCombo;
import com.yxm.po.SysElder;
import com.yxm.vo.H5SelectVo;

import java.util.List;

public interface ReservationService {
    List<H5SelectVo> selectBerthGrade();
    SysAppointment selectElderNurse(Integer elderId);
    List<H5SelectVo> selectBreakfast(Integer comboGrade,Integer comboType);
    List<H5SelectVo> selectLunch(Integer comboGrade,Integer comboType);
    List<H5SelectVo> selectDinner(Integer comboGrade,Integer comboType);
    SysCombo selectComboById(Integer comboId);
}
