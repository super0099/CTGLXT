package com.yxm.dao;

import com.yxm.po.SysAppointment;
import com.yxm.po.SysCollection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

//预约,办理入住,退出,等功能区
@Repository
public interface AppointmentDao {
    Integer selectAppointmentCount();
    Integer insetAppointment(SysAppointment sysAppointment);

    SysAppointment selectAppointmentByElderId(Integer elderId);
    SysAppointment selectAppointmentByCheckInCode(String checkInCode);
    Integer updateAppointment(Integer id);
    Integer selectArrearage(Integer elderId);
    Integer contract(@Param("endDate") Date endDate,@Param("checkInCode") String checkInCode);
}
