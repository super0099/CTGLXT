package com.yxm.dao;

import com.yxm.po.SysElder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwaitDao {
    List<SysElder> selectAwaitElder();
    Byte collectionState(@Param("elderId") Integer elderId);
    Integer updateAppointmentById(Integer appointmentId);
    Integer updateBerthById(Integer berthId);
}
