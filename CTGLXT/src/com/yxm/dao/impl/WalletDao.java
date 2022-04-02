package com.yxm.dao.impl;

import com.yxm.dao.IWalletDao;
import com.yxm.po.dbConsumption;
import com.yxm.util.JdbcUtils;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletDao implements IWalletDao {
    private static final String selectBalance = "SELECT balance FROM db_wallet WHERE userId=?";
    private static final String selectConsumption="SELECT Id,establishDate,ctype,userId,balance,money FROM db_consumption WHERE userId=? ORDER BY establishDate DESC";
    private static final String rechargeMoney = "UPDATE db_wallet SET balance = balance+? WHERE userId=?";
    private static final String consumption = "UPDATE db_wallet SET balance = balance-? WHERE userId=?";
    private static final String Addconsumption = "INSERT INTO db_consumption(establishDate,ctype,userId,balance,money) VALUE(NOW(),?,?,?,?)";
    @Override
    public BigDecimal selectBalance(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BigDecimal balance = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectBalance);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                balance = rs.getBigDecimal("balance");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return balance;
    }

    @Override
    public List<dbConsumption> selectConsumption(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<dbConsumption> dbConsumptionList = new ArrayList<>();
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectConsumption);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbConsumption dbConsumption = new dbConsumption();
                dbConsumption.setBalance(rs.getBigDecimal("balance"));
                dbConsumption.setCtype(rs.getString("ctype"));
                dbConsumption.setUserId(rs.getInt("userId"));
                dbConsumption.setEstablishDate(new Date(rs.getTimestamp("establishDate").getTime()));
                dbConsumption.setMoney(rs.getBigDecimal("money"));
                dbConsumption.setId(rs.getInt("Id"));
                dbConsumptionList.add(dbConsumption);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbConsumptionList;
    }

    @Override
    public boolean rechargeMoney(Integer userId, BigDecimal money) {
        boolean boolR = false;
        Connection conn= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(rechargeMoney);
            ps.setBigDecimal(1,money);
            ps.setInt(2,userId);
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public boolean Addconsumption(dbConsumption consumption) {
        boolean boolR = false;
        Connection conn= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(Addconsumption);
            ps.setString(1,consumption.getCtype());
            ps.setInt(2,consumption.getUserId());
            ps.setBigDecimal(3,consumption.getBalance());
            ps.setBigDecimal(4,consumption.getMoney());
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public boolean consumption(Integer userId, BigDecimal money) {
        boolean boolR = false;
        Connection conn= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(consumption);
            ps.setBigDecimal(1,money);
            ps.setInt(2,userId);
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }


}
