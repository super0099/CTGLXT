package com.yxm.dao.impl;

import com.yxm.dao.IPreferentialDao;
import com.yxm.po.dbDiiscounts;
import com.yxm.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreferentialDao implements IPreferentialDao {
    private static final String selectPreferentialCount = "SELECT COUNT(Id) FROM db_discounts WHERE userId = ?";
    private static final String selectPreferentialList ="SELECT Id,discountsName,discount,userId FROM db_discounts WHERE userId=?";
    @Override
    public int selectPreferentialCount(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectPreferentialCount);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return count;
    }

    @Override
    public List<dbDiiscounts> selectPreferentialList(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<dbDiiscounts> dbDiiscountsList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectPreferentialList);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbDiiscounts dbDiiscounts = new dbDiiscounts();
                dbDiiscounts.setDiscount(rs.getBigDecimal("discount"));
                dbDiiscounts.setDiscountsName(rs.getString("discountsName"));
                dbDiiscounts.setId(rs.getInt("Id"));
                dbDiiscounts.setUserId(rs.getInt("userId"));
                dbDiiscountsList.add(dbDiiscounts);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbDiiscountsList;
    }
}
