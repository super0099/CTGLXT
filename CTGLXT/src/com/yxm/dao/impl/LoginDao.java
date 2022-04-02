package com.yxm.dao.impl;

import com.yxm.dao.ILoginDao;
import com.yxm.po.dbUser;
import com.yxm.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao implements ILoginDao {
    private static String selectUser = "SELECT Id,userName,userPassword,userType,userPrivilege,userPhone,userIdnumber,nickname,portrait FROM db_user WHERE userName = ?";
    private static String selectCountPhone = "SELECT COUNT(id) FROM db_user WHERE userPhone=?";
    private static String insertUser = "INSERT INTO db_user(userName,userPassword,userType,userPrivilege,userPhone) VALUE(?,?,?,?,?)";
    /**
     * 根据用户名查询该账号
     * @param suerName
     * @return
     */
    @Override
    public dbUser selectUser(String suerName) {
        Connection conn= null;
        PreparedStatement ps = null;
        ResultSet rs =null;
        dbUser dbuser = null;
        try {
            conn= JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectUser);
            ps.setString(1,suerName);
            rs = ps.executeQuery();
            while (rs.next()){
                dbuser = new dbUser();
                //Id,userName,userPassword,userType,userPrivilege,userPhone,userIdnumber,nickname,portrait
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

    @Override
    public int selectCountPhone(String phone) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectCountPhone);
            ps.setString(1,phone);
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
    public boolean insertUser(dbUser dbuser) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(insertUser);
            //userName,userPassword,userType,userPrivilege,userPhone
            ps.setString(1,dbuser.getUserName());
            ps.setString(2,dbuser.getUserPassword());
            ps.setInt(3,dbuser.getUserType());
            ps.setInt(4,dbuser.getUserPrivilege());
            ps.setString(5,dbuser.getUserPhone());
            int rool = ps.executeUpdate();
            boolR = rool==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }
}
