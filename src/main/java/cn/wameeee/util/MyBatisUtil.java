package cn.wameeee.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis工具类，用于获取SqlSessionFactory和SqlSession
 */
public class MyBatisUtil {
    private static final Logger logger = Logger.getLogger(MyBatisUtil.class);
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 加载MyBatis配置文件
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            // 创建SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            logger.info("初始化SqlSessionFactory成功");
        } catch (IOException e) {
            logger.error("初始化SqlSessionFactory失败", e);
            throw new RuntimeException("初始化SqlSessionFactory失败", e);
        }
    }

    /**
     * 获取SqlSessionFactory
     * @return SqlSessionFactory对象
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 获取SqlSession
     * @return SqlSession对象
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }

    /**
     * 获取自动提交的SqlSession
     * @return 自动提交的SqlSession对象
     */
    public static SqlSession getSqlSessionAutoCommit() {
        return sqlSessionFactory.openSession(true);
    }

    /**
     * 关闭SqlSession
     * @param sqlSession 要关闭的SqlSession对象
     */
    public static void closeSqlSession(SqlSession sqlSession) {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
} 