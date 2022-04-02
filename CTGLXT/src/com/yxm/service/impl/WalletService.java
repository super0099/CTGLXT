package com.yxm.service.impl;

import com.yxm.dao.IWalletDao;
import com.yxm.dao.impl.WalletDao;
import com.yxm.po.dbConsumption;
import com.yxm.service.IWalletService;
import com.yxm.util.JdbcUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class WalletService implements IWalletService {
    private IWalletDao walletDao = new WalletDao();
    @Override
    public BigDecimal selectBalance(Integer userId) {
        return this.walletDao.selectBalance(userId);
    }

    @Override
    public List<dbConsumption> selectConsumption(Integer userId) {
        return this.walletDao.selectConsumption(userId);
    }

    @Override
    public boolean recharge(Integer userId, BigDecimal money) {
        boolean boolR =false;
        try {
            JdbcUtils.beginTransaction();
            BigDecimal moneyCount = this.walletDao.selectBalance(userId);
            boolean isOk = this.walletDao.rechargeMoney(userId,money);
            if(!isOk){
                throw new SQLException("充值失败");
            }
            dbConsumption consumption = new dbConsumption();
            consumption.setMoney(money);
            consumption.setBalance(moneyCount.add(money));
            consumption.setCtype("充值");
            consumption.setUserId(userId);
            boolean ok = this.walletDao.Addconsumption(consumption);
            if(ok){
                boolR =true;
                JdbcUtils.commitTransaction();
            }else {
                throw new SQLException("收藏失败");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return boolR;
    }


}
