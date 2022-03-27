package com.yxm.service.impl;

import com.yxm.dao.CollectionDao;
import com.yxm.dao.CostDao;
import com.yxm.po.SysCollection;
import com.yxm.service.CollectionService;
import com.yxm.vo.CollectionAndNursetype;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private CostDao costDao;
    @Override
    public SysCollection selectElderOrder(Integer elderId,String years) {
        return this.collectionDao.selectElderOrder(elderId,years);
    }
    @Override
    public LayuiTableData<CollectionAndNursetype> selectElderPayment(Integer elderId) {
        Integer count = this.costDao.selectPaymentCount(elderId);
        List<CollectionAndNursetype> collectionAndNursetypes = this.collectionDao.selectElderPayment(elderId);
        return new LayuiTableData<>(count,collectionAndNursetypes);
    }

    @Override
    public LayuiTableData<CollectionAndNursetype> selectElderAllSettle(Integer elderId) {
        Integer count = this.costDao.selectElderAllSettleCount(elderId);
        List<CollectionAndNursetype> collectionAndNursetypes = this.collectionDao.selectElderAllSettle(elderId);
        return new LayuiTableData<>(count,collectionAndNursetypes);
    }

    @Override
    public LayuiTableData<CollectionAndNursetype> selectArrearageElder(Integer elderId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        Integer years = Integer.parseInt(simpleDateFormat.format(date));
        Integer count = this.costDao.selectArrearageElderCount(elderId,years,date.getMonth()+1);
        List<CollectionAndNursetype> data = this.collectionDao.selectArrearageElder(elderId,years,date.getMonth()+1);
        return new LayuiTableData<>(count,data);
    }

}
