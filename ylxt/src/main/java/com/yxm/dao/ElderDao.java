package com.yxm.dao;

import com.yxm.po.SysElder;
import com.yxm.vo.ElderAndBerth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
//长者信息
import java.util.List;
@Repository
public interface ElderDao {
    List<ElderAndBerth> selectElderAll(@Param("page") Integer page,@Param("limit") Integer limit,@Param("elderName")String elderName,@Param("berthNumber")String berthNumber);
    Integer selectElderCount();
    Integer insertElder(SysElder sysElder);
    SysElder selectElder(String checkInCode);
    SysElder selectElderById(Integer elderId);
    Integer updateElder(SysElder sysElder);
    Integer deleteElderById(Integer elderId);
    SysElder selectElderByAppointmentId(Integer appointmentId);
    Integer updateElderById(Integer id);
    List<SysElder> selectArrearageElder(@Param("years") Integer years,@Param("months") Integer months);
    List<SysElder> selectCheckInAll();
}
