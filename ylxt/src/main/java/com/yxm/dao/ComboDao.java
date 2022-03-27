package com.yxm.dao;

import com.yxm.po.SysCombo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboDao {
    List<SysCombo> selectBreakfast(@Param("comboGrade") Integer comboGrade, @Param("comboType") Integer comboType);
    SysCombo selectComboById(Integer comboId);
    List<SysCombo> selectComboAll();
}
