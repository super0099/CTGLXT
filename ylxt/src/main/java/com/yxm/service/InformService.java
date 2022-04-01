package com.yxm.service;

import com.yxm.po.SysElder;
import com.yxm.po.SysInvoice;

import java.util.List;

public interface InformService {
    List<SysElder> selectElderInform();
    boolean updateCollection(Integer collectionId, SysInvoice sysInvoice, String userName);
}
