package cn.wameeee.service;

import cn.wameeee.entity.AuctionUser;
import java.util.List;

/**
 * 用户业务接口
 */
public interface AuctionUserService {
    
    /**
     * 用户登录
     * @param userName 用户名
     * @param userPassword 密码
     * @return 用户对象，登录失败返回null
     */
    AuctionUser login(String userName, String userPassword);
    
    /**
     * 用户注册
     * @param user 用户对象
     * @return 是否注册成功
     */
    boolean register(AuctionUser user);
    
    /**
     * 根据ID查询用户
     * @param userId 用户ID
     * @return 用户对象
     */
    AuctionUser getUserById(Long userId);
    
    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 用户对象
     */
    AuctionUser getUserByName(String userName);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<AuctionUser> getAllUsers();
    
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 是否更新成功
     */
    boolean updateUser(AuctionUser user);
    
    /**
     * 删除用户
     * @param userId 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(Long userId);
} 