package com.yxm.service;

import com.yxm.po.SysBondsman;
import com.yxm.vo.LayuiTableData;

public interface BondsmanService {
    LayuiTableData<SysBondsman> selectBondsmanAll(Integer page,Integer limit,String elderName,String bondsmanName,String phone);
    SysBondsman selectBondsmanById(Integer bondsmanId);
    boolean updateBondsman(SysBondsman sysBondsman);
    Integer selectElderB(Integer elderId);
    boolean deleteBondsman(Integer bondsmanId);
}
