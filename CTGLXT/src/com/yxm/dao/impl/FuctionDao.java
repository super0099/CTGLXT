package com.yxm.dao.impl;

import com.yxm.dao.IFuctionDao;
import com.yxm.po.dbUser;
import com.yxm.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuctionDao implements IFuctionDao {
    private static String alteruser = "UPDATE db_user SET portrait = ?,nickname = ? WHERE id = ?";
    private static String selectUser = "SELECT Id,userName,userPassword,userType,userPrivilege,userPhone,userIdnumber,nickname,portrait FROM db_user WHERE Id = ?";
    //修改用户
    @Override
    public boolean alteruser(dbUser dbuser) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean booR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(alteruser);
            ps.setString(1,dbuser.getPortrait());
            ps.setString(2,dbuser.getNickname());
            ps.setInt(3,dbuser.getId());
            int rowData = ps.executeUpdate();
            booR = rowData==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return booR;
    }

    @Override
    public dbUser selectUser(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        dbUser dbuser = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectUser);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbuser = new dbUser();
                dbuser.setId(rs.getInt("Id"));
                dbuser.setNickname(rs.getString("nickname"));
                dbuser.setPortrait(rs.getString("portrait"));
                dbuser.setUserIdnumber(rs.getString("userIdnumber"));
                dbuser.setUserName(rs.getString("userName"));
                dbuser.setUserPassword(rs.getString("userPassword"));
                dbuser.setUserPrivilege(rs.getInt("userPrivilege"));
                dbuser.setUserPhone(rs.getString("userPhone"));
                dbuser.setUserType(rs.getInt("userType"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbuser;
    }
}
