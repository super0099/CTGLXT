package com.yxm.service;

import com.yxm.po.SysElder;
import com.yxm.po.SysInvoice;
import com.yxm.vo.ElderIndentData;

import java.util.List;

public interface AwaitService {
    List<SysElder> selectAwaitElderAll();
    ElderIndentData selectAwaitElder(Integer elderId);
    boolean updateCollection(Integer collectionId, SysInvoice sysInvoice, String userName,Integer berthId,Integer appointmentId,String checkInCode);
    boolean elderCheckIn(Integer berthId,Integer appointmentId);
}
