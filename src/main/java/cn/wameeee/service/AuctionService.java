package cn.wameeee.service;

import cn.wameeee.entity.Auction;
import java.util.List;

/**
 * 拍卖品业务接口
 */
public interface AuctionService {
    
    /**
     * 根据ID查询拍卖品
     * @param auctionId 拍卖品ID
     * @return 拍卖品对象
     */
    Auction getAuctionById(Long auctionId);
    
    /**
     * 查询所有拍卖品
     * @return 拍卖品列表
     */
    List<Auction> getAllAuctions();
    
    /**
     * 查询正在进行中的拍卖品
     * @return 拍卖品列表
     */
    List<Auction> getOngoingAuctions();
    
    /**
     * 查询已结束的拍卖品
     * @return 拍卖品列表
     */
    List<Auction> getFinishedAuctions();
    
    /**
     * 根据名称模糊查询拍卖品
     * @param auctionName 拍卖品名称
     * @return 拍卖品列表
     */
    List<Auction> getAuctionsByName(String auctionName);
    
    /**
     * 添加拍卖品
     * @param auction 拍卖品对象
     * @return 是否添加成功
     */
    boolean addAuction(Auction auction);
    
    /**
     * 更新拍卖品
     * @param auction 拍卖品对象
     * @return 是否更新成功
     */
    boolean updateAuction(Auction auction);
    
    /**
     * 删除拍卖品
     * @param auctionId 拍卖品ID
     * @return 是否删除成功
     */
    boolean deleteAuction(Long auctionId);
} 