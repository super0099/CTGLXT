package com.yxm.service;

import com.yxm.po.SysBondsman;
import com.yxm.po.SysElder;

import java.util.List;

public interface ExpireService {
    List<SysElder> selectExpireElder();
    List<SysBondsman> selectElderBondsman(String appointmentId);
}
