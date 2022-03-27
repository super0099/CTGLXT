package com.yxm.dao;

import com.yxm.vo.NursetTypeCost;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumeDao {
    NursetTypeCost selectNurseTypeCost(String appointmentId);
}
