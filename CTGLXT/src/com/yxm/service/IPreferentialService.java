package com.yxm.service;

import com.yxm.po.dbDiiscounts;

import java.util.List;

public interface IPreferentialService {
    int selectPreferentialCount(Integer userId);
    List<dbDiiscounts> selectPreferentialList(Integer userId);
}
