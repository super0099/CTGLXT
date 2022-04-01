package com.yxm.dao;

import com.yxm.po.SysElder;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpireDao {
    List<SysElder> selectExpireElder();
    List<SysElder> selectExpireElderEd();
}
