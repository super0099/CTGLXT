package com.yxm.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MybatisUtils {
    /*
    SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由对它进行清除或重建。
    使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏味道（bad smell）”。
    因此 SqlSessionFactory 的最佳范围是应用范围。
    有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。
     */
    private static SqlSessionFactory sqlSessionFactory =null;
    //静态初始化代码块
    static {
        try {
            Properties properties = new Properties();
//            properties.setProperty("username","yxm");
            String resource = "mybatis-conf.xml";//MyBatis的核心配置
            InputStream inputStream =  Resources.getResourceAsStream(resource);//读取配置文件
            //创建SqlSessionFactory
            //这个类可以被实例化,使用和丢弃,一旦创建了SqlSessionFactory,就不再需要它了.
            //因此SqlSessionFactoryBuilder 实例的最佳范围是方法范围(也就是局部方法变量).
            sqlSessionFactory = new SqlSessionFactoryBuilder()
                    // .build(inputStream);//只根据配置文件构建
                    // .build(inputStream,properties);//配置文件,指定的属性
                    .build(inputStream,"development",properties);//配置文件,指定使用的环境id,指定的属性
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取SqlSession
     * 默认不自动提交，开启JDBC事务
     * @return SqlSession
     */
    public static SqlSession getSqlSession(){
         /*
        它会创建有如下特性的 SqlSession:
        1、会开启一个事务(也就是不自动提交)  false
        2、连接对象会从由活动环境配置的数据源实例中得到。
        3、事务隔离级别将会使用驱动或数据源的默认设置。
        4、预处理语句不会被复用,也不会批量处理更新。
         */
        return sqlSessionFactory.openSession();
    }

    /**
     * 获取SqlSession
     * @param isAutoCommit
     * @return
     */
    public SqlSession getSqlSessio(boolean isAutoCommit){
        return sqlSessionFactory.openSession(isAutoCommit);
    }
    /**
     * 提交&释放资源
     */
    public static void commitAndClose(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 回滚&释放资源
     */
    public static void rollbackAndClose(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
