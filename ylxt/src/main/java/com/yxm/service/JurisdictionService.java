package com.yxm.service;

import com.yxm.po.SysPosition;
import com.yxm.util.JsonMsg;
import com.yxm.vo.LayuiTableData;
import com.yxm.vo.LayuiTreeVo;

import java.util.List;

public interface JurisdictionService {
    LayuiTableData<SysPosition> selectPageList(Integer page,Integer limit,String positionName);
    SysPosition selectById(Integer positionId);
    boolean insert(SysPosition sysPosition);
    boolean update(SysPosition sysPosition);
    JsonMsg deleteById(Integer positionId);
    JsonMsg authorize(int roleId, List<Integer> selectMenuIdList);
    List<LayuiTreeVo> selectMenuForLayuiTree(Integer positionId);
}
