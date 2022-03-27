package com.yxm.service;

import com.yxm.po.SysAppointment;
import com.yxm.po.SysElder;
import com.yxm.vo.BondsmanList;

import java.text.ParseException;

public interface AppointmentService {
    Integer selectAppointmentCount();

    boolean insertAppointmentData(SysAppointment sysAppointment, BondsmanList bondsmanList) throws ParseException;

    SysElder selectElder(String checkInCode);

    SysAppointment selectAppointmentByElderId(Integer elderId);

    SysAppointment selectAppointmentByCheckInCode(String checkInCode);
}
