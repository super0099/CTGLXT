package com.yxm.service.impl;

import com.yxm.dao.IOrderDao;
import com.yxm.dao.IWalletDao;
import com.yxm.dao.impl.OrderDao;
import com.yxm.dao.impl.WalletDao;
import com.yxm.po.dbConsumption;
import com.yxm.po.dbMenu;
import com.yxm.po.dbMenuOrder;
import com.yxm.po.dbWorderForm;
import com.yxm.service.IOrderService;
import com.yxm.util.JdbcUtils;
import com.yxm.vo.AddShopingCar;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class OrderService implements IOrderService {
    private IOrderDao orderDao = new OrderDao();
    private IWalletDao walletDao = new WalletDao();
    @Override
    public dbMenu selectMenu(Integer menuId) {
        return this.orderDao.selectMenu(menuId);
    }

    @Override
    public boolean addOrder(dbWorderForm worderForm,dbMenuOrder dbMenuOrder) {
        boolean boolR = false;
        try {
            JdbcUtils.beginTransaction();
            boolean isOK = this.orderDao.addOrder(worderForm);
            if(!isOK){
                throw new SQLException("订单创建失败");
            }
            dbWorderForm dbWorderForm = this.orderDao.selectOrder(worderForm.getOrderNumber());
            dbMenuOrder.setOrderFormId(dbWorderForm.getId());
            boolean OK = this.orderDao.addMenuOrder(dbMenuOrder);
            if(OK){
                boolR=true;
                JdbcUtils.commitTransaction();
            }else {
                throw new SQLException("操作错误");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                JdbcUtils.rollbackTransaction();//事务回滚
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return boolR;
    }

    @Override
    public boolean addOrderList(dbWorderForm worderForm, List<AddShopingCar> addShopingCar,Integer carId) {
        boolean boolR = false;
        BigDecimal totalPrice = new BigDecimal(0);
        for (AddShopingCar menuPrice:addShopingCar){
            BigDecimal Price = menuPrice.getPrice();
            BigDecimal Amount = new BigDecimal(menuPrice.getAmount());
            BigDecimal total = Price.multiply(Amount);
            totalPrice=totalPrice.add(total);
        }
        BigDecimal percentage= new BigDecimal(0.1);
        worderForm.setPrice(totalPrice);
        worderForm.setTip(totalPrice.multiply(percentage));
        try {
            JdbcUtils.beginTransaction();
            boolean isOK = this.orderDao.addOrder(worderForm);
            if(!isOK){
                throw new SQLException("订单创建失败");
            }
            dbWorderForm dbWorderForm = this.orderDao.selectOrder(worderForm.getOrderNumber());
            for (AddShopingCar menuPrice:addShopingCar){
                dbMenuOrder dbMenuOrder = new dbMenuOrder();
                dbMenuOrder.setQuantity(menuPrice.getAmount());
                dbMenuOrder.setOrderType(1);
                dbMenuOrder.setOrderFormId(dbWorderForm.getId());
                dbMenuOrder.setNemuId(menuPrice.getId());
                boolean OK = this.orderDao.addMenuOrder(dbMenuOrder);
                if(!OK){
                    throw new SQLException("订单菜类创建失败");
                }
            }
            boolean O = this.orderDao.deleteCarMneu(carId);
            if(O){
                JdbcUtils.commitTransaction();
                boolR = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                JdbcUtils.rollbackTransaction();//事务回滚
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return boolR;
    }

    @Override
    public boolean orderPayment(Integer orderId, Integer userId) {
        boolean boolR =false;
        BigDecimal money = this.walletDao.selectBalance(userId);
        dbWorderForm dbWorderForm = this.orderDao.selectrWorderForm(orderId);
        try {
            JdbcUtils.beginTransaction();
            if(money.compareTo(dbWorderForm.getPrice())==0||money.compareTo(dbWorderForm.getPrice())==1){
                boolean isOk = this.walletDao.consumption(userId,dbWorderForm.getPrice());
                if(!isOk){
                    throw new SQLException("支付失败");
                }
            }else {
                throw new SQLException("余额额不足");
            }
            boolean isOk = this.orderDao.updataOrderState(orderId);
            if(!isOk){
                throw new SQLException("修改订单状态失败");
            }
            dbConsumption consumption = new dbConsumption();
            consumption.setMoney(dbWorderForm.getPrice());
            consumption.setBalance(money.subtract(dbWorderForm.getPrice()));
            consumption.setCtype("订单消费");
            consumption.setUserId(userId);
            boolean Ok = this.walletDao.Addconsumption(consumption);
            if(Ok){
                boolR=true;
                JdbcUtils.commitTransaction();
            }else {
                throw new SQLException("消费记录新增失败");
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

    @Override
    public List<AddShopingCar> selectCarMenu(Integer carId) {
        return this.orderDao.selectCarMenu(carId);
    }
}
