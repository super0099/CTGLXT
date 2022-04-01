package com.yxm.service;

import com.yxm.vo.NursetTypeCost;

public interface ConsumeService {
    NursetTypeCost selectNurseTypeCost(String appointmentId);
}
