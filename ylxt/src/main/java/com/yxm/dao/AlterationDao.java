package com.yxm.dao;

import com.yxm.po.SysAlteration;
import org.springframework.stereotype.Repository;
@Repository
public interface AlterationDao {
    Integer insertAlteration(SysAlteration sysAlteration);
}
