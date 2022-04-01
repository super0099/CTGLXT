package com.yxm.service;

import com.yxm.po.SysElder;

import java.util.Date;
import java.util.List;

public interface UnsubscribeService {
    List<SysElder> selectExpireElder();
    boolean deleteByElderId(Integer elderId);
    boolean contract(Date endDate, String checkInCode);
    Integer selectArrearage(Integer elderId);
}
