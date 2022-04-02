package com.yxm.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC 工具类
 * 支持Service事务（需要在Service中手动开启事务beginTransaction、提交事务commitTransaction/回滚事务rollbackTransaction
 */
public class JdbcUtils {
    // 它是事务专用连接, 并且每个线程分配一个Connection
    private static final ThreadLocal<Connection> tl = new ThreadLocal<>();

    private static String url = "";
    private static String username = "";
    private static String password = "";

    static {
        Properties properties = new Properties();
        try {
            //加载jdbc配置文件
            InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(in);
            //读取参数
            String driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            //加载驱动
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        // 获取当前线程的 con
        Connection con = tl.get();
        // 当 con 不等于 null, 说明已经调用过 beginTransaction() 方法了.表示开启了事务.
        if (con != null) return con;
        //当 con 等于 null,说明使用全新的连接
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 开启事务的
     *
     * 创建一个 Connection, 设置为手动提交setAutoCommit(false)
     * 还要让 commitTransaction 或 rollbackTransaction 以及 DAO 可以获取到!!
     * @throws SQLException Sql异常
     */
    public static void beginTransaction() throws SQLException {
        // 获取当前线程的con
        Connection con = tl.get();
        //判断事务是否已经开启，如果connection已经存在就说明已经开启
        if (con == null){
            // con为null,准备开启事务
            con = getConnection();// 获取一个新的connection，
            con.setAutoCommit(false);// 禁止自动提交
            tl.set(con); // 把当前线程的连接保存起来.
        }
    }

    /**
     * 提交事务
     *
     * 获取 beginTransaction 提供的 Connection, 然后调用 rollback 方法.
     * @throws SQLException Sql异常
     */
    public static void commitTransaction() throws SQLException {
        // 获取当前线程的连接
        Connection con = tl.get();
        if (con == null) throw new SQLException("还没有开启事务,不能提交!");
        con.commit();//提交事务
        con.close();//关闭连接
        // 从 tl 中移除连接
        tl.remove();
    }

    /**
     * 回滚事务
     * 获取 beginTransaction 提供的 Connection, 然后调用 rollback 方法.
     * @throws SQLException Sql异常
     */
    public static void rollbackTransaction() throws SQLException {
        // 获取当前线程的连接
        Connection con = tl.get();
        if (con == null) throw new SQLException("还没有开启事务,不能回滚!");
        con.rollback();//回滚事务
        con.close();//关闭连接
        // 从 tl 中移除连接
        tl.remove();
    }

    /**
     * 关闭资源
     * @param conn 连接（会根据情况判断）
     * @param ps PreparedStatement对象
     * @param rs ResultSet对象
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        // 获取当前线程的连接
        Connection conTl = tl.get(); // 获取当前线程的连接
        try {
            //判断conn不为空，且不为当前线程的连接（当前线程的连接开启了事务，不能在这里关闭）
            if (conn!=null && conn!=conTl) {
                conn.close();
            }
            if (ps!=null) {
                ps.close();
            }
            if (rs!=null) {
                rs.close();
            }
        } catch (SQLException e2) {
            // TODO: handle exception
        }
    }
}