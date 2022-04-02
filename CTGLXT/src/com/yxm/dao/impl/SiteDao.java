package com.yxm.dao.impl;

import com.yxm.dao.ISiteDao;
import com.yxm.po.dbSite;
import com.yxm.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SiteDao implements ISiteDao {
    private static final String selectSite = "SELECT Id,userId,site,linkman,phone FROM db_site WHERE userId=?";
    private static final String selectSiteData = "SELECT Id,userId,site,linkman,phone FROM db_site WHERE Id=?";
    private static final String addSite="INSERT INTO db_site(userId,site,linkman,phone) VALUE(?,?,?,?)";
    private static final String compileSite = "UPDATE  db_site SET site = ?,linkman=?,phone=? WHERE Id=?";
    private static final String deleteSite = "DELETE FROM db_site WHERE Id=?";
    @Override
    public List<dbSite> selectSite(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<dbSite> dbSiteList = new ArrayList<>();
        dbSite dbSite = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectSite);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbSite = new dbSite();
                dbSite.setSite(rs.getString("site"));
                dbSite.setId(rs.getInt("Id"));
                dbSite.setLinkman(rs.getString("linkman"));
                dbSite.setPhone(rs.getString("phone"));
                dbSiteList.add(dbSite);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbSiteList;
    }

    @Override
    public dbSite selectSiteData(Integer siteId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        dbSite dbSite = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectSiteData);
            ps.setInt(1,siteId);
            rs = ps.executeQuery();
            while (rs.next()){
                //Id,userId,site,linkman,phone
                dbSite = new dbSite();
                dbSite.setPhone(rs.getString("phone"));
                dbSite.setSite(rs.getString("site"));
                dbSite.setLinkman(rs.getString("linkman"));
                dbSite.setId(rs.getInt("Id"));
                dbSite.setUserId(rs.getInt("userId"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbSite;
    }

    @Override
    public boolean addSite(dbSite dbSite) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(addSite);
            ps.setInt(1,dbSite.getUserId());
            ps.setString(2,dbSite.getSite());
            ps.setString(4,dbSite.getPhone());
            ps.setString(3,dbSite.getLinkman());
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public boolean compileSite(dbSite dbSite, Integer siteId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs =null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(compileSite);
            ps.setString(1,dbSite.getSite());
            ps.setString(2,dbSite.getLinkman());
            ps.setString(3,dbSite.getPhone());
            ps.setInt(4,siteId);
            boolR=ps.executeUpdate()==1;
//            ps = conn.prepareStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public boolean deleteSite(Integer siteId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(deleteSite);
            ps.setInt(1,siteId);
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }
}
