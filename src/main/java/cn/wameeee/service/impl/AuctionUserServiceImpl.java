package cn.wameeee.service.impl;

import cn.wameeee.entity.AuctionUser;
import cn.wameeee.mapper.AuctionUserMapper;
import cn.wameeee.service.AuctionUserService;
import cn.wameeee.util.MyBatisUtil;
import cn.wameeee.util.PasswordUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 用户业务实现类
 */
public class AuctionUserServiceImpl implements AuctionUserService {

    /**
     * 用户登录
     * @param userName 用户名
     * @param userPassword 密码
     * @return 用户对象，登录失败返回null
     */
    @Override
    public AuctionUser login(String userName, String userPassword) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionUserMapper mapper = sqlSession.getMapper(AuctionUserMapper.class);
            AuctionUser user = mapper.selectByName(userName);
            if (user != null && PasswordUtil.verifyPassword(userPassword, user.getUserPassword())) {
                return user;
            }
            return null;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 用户注册
     * @param user 用户对象
     * @return 是否注册成功
     */
    @Override
    public boolean register(AuctionUser user) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            // 检查用户名是否已存在
            AuctionUserMapper mapper = sqlSession.getMapper(AuctionUserMapper.class);
            AuctionUser existUser = mapper.selectByName(user.getUserName());
            if (existUser != null) {
                return false;
            }
            
            // 设置默认值
            if (user.getUserIsAdmin() == null) {
                user.setUserIsAdmin(false);
            }
            
            // 加密密码
            String encryptedPassword = PasswordUtil.encryptPassword(user.getUserPassword());
            user.setUserPassword(encryptedPassword);
            
            int rows = mapper.insert(user);
            sqlSession.commit();
            return rows > 0;
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            return false;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据ID查询用户
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public AuctionUser getUserById(Long userId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionUserMapper mapper = sqlSession.getMapper(AuctionUserMapper.class);
            return mapper.selectById(userId);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 用户对象
     */
    @Override
    public AuctionUser getUserByName(String userName) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionUserMapper mapper = sqlSession.getMapper(AuctionUserMapper.class);
            return mapper.selectByName(userName);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询所有用户
     * @return 用户列表
     */
    @Override
    public List<AuctionUser> getAllUsers() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionUserMapper mapper = sqlSession.getMapper(AuctionUserMapper.class);
            return mapper.selectAll();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 是否更新成功
     */
    @Override
    public boolean updateUser(AuctionUser user) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            // 如果密码不为空，则加密
            if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
                String encryptedPassword = PasswordUtil.encryptPassword(user.getUserPassword());
                user.setUserPassword(encryptedPassword);
            }
            
            AuctionUserMapper mapper = sqlSession.getMapper(AuctionUserMapper.class);
            int rows = mapper.update(user);
            sqlSession.commit();
            return rows > 0;
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            return false;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteUser(Long userId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionUserMapper mapper = sqlSession.getMapper(AuctionUserMapper.class);
            int rows = mapper.delete(userId);
            sqlSession.commit();
            return rows > 0;
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            return false;
        } finally {
            sqlSession.close();
        }
    }
} 