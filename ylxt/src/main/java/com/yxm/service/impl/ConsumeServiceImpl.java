package com.yxm.service.impl;

import com.yxm.dao.ConsumeDao;
import com.yxm.service.ConsumeService;
import com.yxm.vo.NursetTypeCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumeServiceImpl implements ConsumeService {
    @Autowired
    private ConsumeDao consumeDao;
    @Override
    public NursetTypeCost selectNurseTypeCost(String appointmentId) {
        return this.consumeDao.selectNurseTypeCost(appointmentId);
    }
}
