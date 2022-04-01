package com.yxm.dao;

import com.yxm.po.SysElder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformDao {
    List<SysElder> selectElderInform();
    Integer updateCollection(Integer collectionId);
}
