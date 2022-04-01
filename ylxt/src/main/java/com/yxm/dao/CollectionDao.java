package com.yxm.dao;

import com.yxm.po.SysCollection;
import com.yxm.vo.CollectionAndNursetype;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

//对长者的月份,订单修改
@Repository
public interface CollectionDao {
    SysCollection selectElderOrder(@Param("elderId") Integer elderId,@Param("years")String years);
    List<CollectionAndNursetype> selectElderPayment(Integer elderId);
    List<CollectionAndNursetype> selectElderAllSettle(Integer elderId);
    List<CollectionAndNursetype> selectArrearageElder(@Param("elderId") Integer elderId,@Param("years") Integer years,@Param("months")Integer months);
    SysCollection selectCollection(@Param("elderId") Integer elderId,@Param("years")String years,@Param("months")Integer months);
}
