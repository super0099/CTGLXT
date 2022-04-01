package com.yxm.dao;

import com.yxm.po.SysBondsman;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//担保人修改
@Repository
public interface BondsmanDao {
    List<SysBondsman> selectBondsmanAll(@Param("page") Integer page,
                                        @Param("limit") Integer limit,
                                        @Param("elderName")String elderName,
                                        @Param("bondsmanName")String bondsmanName,
                                        @Param("phone")String phone);
    List<SysBondsman> selectElderBondsman(String appointmentId);

    Integer selectBondsmanCount();

    Integer insertBondsman(SysBondsman sysBondsman);

    SysBondsman selectBondsmanById(Integer bondsmanId);

    Integer updateBondsman(SysBondsman sysBondsman);

    Integer selectElderB(Integer elderId);
    Integer deleteBondsman(Integer bondsmanId);
}
