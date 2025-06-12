package cn.wameeee.mapper;

import cn.wameeee.entity.AuctionUser;
import java.util.List;

/**
 * AuctionUser的Mapper接口
 */
public interface AuctionUserMapper {
    
    /**
     * 根据用户ID查询用户
     * @param userId 用户ID
     * @return 用户对象
     */
    AuctionUser selectById(Long userId);
    
    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return 用户对象
     */
    AuctionUser selectByUserName(String userName);
    
    /**
     * 根据用户名查询用户（别名方法）
     * @param userName 用户名
     * @return 用户对象
     */
    AuctionUser selectByName(String userName);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<AuctionUser> selectAll();
    
    /**
     * 插入用户
     * @param user 用户对象
     * @return 影响的行数
     */
    int insert(AuctionUser user);
    
    /**
     * 更新用户
     * @param user 用户对象
     * @return 影响的行数
     */
    int update(AuctionUser user);
    
    /**
     * 删除用户
     * @param userId 用户ID
     * @return 影响的行数
     */
    int delete(Long userId);
    
    /**
     * 用户登录验证
     * @param userName 用户名
     * @param userPassword 密码
     * @return 用户对象
     */
    AuctionUser login(String userName, String userPassword);
} 