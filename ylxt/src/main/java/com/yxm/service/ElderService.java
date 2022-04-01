package com.yxm.service;

import com.yxm.po.SysAppointment;
import com.yxm.po.SysBondsman;
import com.yxm.po.SysElder;
import com.yxm.vo.ElderAndBerth;
import com.yxm.vo.LayuiTableData;

public interface ElderService {
    LayuiTableData<ElderAndBerth> selectElderAll(Integer page, Integer limit,String elderName,String berthNumber);
    SysElder selectElderById(Integer elderId);
    boolean updateElder(SysElder sysElder);
    boolean deleteElderById(Integer elderId);
    SysElder selectElderByAppointmentId(Integer appointmentId);

}
