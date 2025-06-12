package cn.wameeee.mapper;

import cn.wameeee.entity.Auction;
import java.util.List;

/**
 * Auction的Mapper接口
 */
public interface AuctionMapper {
    
    /**
     * 根据ID查询拍卖品
     * @param auctionId 拍卖品ID
     * @return 拍卖品对象
     */
    Auction selectById(Long auctionId);
    
    /**
     * 查询所有拍卖品
     * @return 拍卖品列表
     */
    List<Auction> selectAll();
    
    /**
     * 查询正在进行中的拍卖品
     * @return 拍卖品列表
     */
    List<Auction> selectOngoing();
    
    /**
     * 查询已结束的拍卖品
     * @return 拍卖品列表
     */
    List<Auction> selectFinished();
    
    /**
     * 根据名称模糊查询拍卖品
     * @param auctionName 拍卖品名称
     * @return 拍卖品列表
     */
    List<Auction> selectByName(String auctionName);
    
    /**
     * 插入拍卖品
     * @param auction 拍卖品对象
     * @return 影响的行数
     */
    int insert(Auction auction);
    
    /**
     * 更新拍卖品
     * @param auction 拍卖品对象
     * @return 影响的行数
     */
    int update(Auction auction);
    
    /**
     * 删除拍卖品
     * @param auctionId 拍卖品ID
     * @return 影响的行数
     */
    int delete(Long auctionId);
} 