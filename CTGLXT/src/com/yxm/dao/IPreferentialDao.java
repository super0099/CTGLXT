package com.yxm.dao;
import com.yxm.po.dbDiiscounts;

import java.util.List;

public interface IPreferentialDao {
    int selectPreferentialCount(Integer userId);
    List<dbDiiscounts> selectPreferentialList(Integer userId);
}
