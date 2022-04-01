package com.yxm.dao;

import com.yxm.vo.ElderAndAlteration;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangeDao {
    Integer selectAlterationCount();
    List<ElderAndAlteration> selectAlterationAll(@Param("page") Integer page,
                                                 @Param("limit") Integer limit,
                                                 @Param("elderName") String elderName,
                                                 @Param("idNumber") String idNumber);
}
