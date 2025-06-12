package cn.wameeee.service;

import cn.wameeee.entity.AuctionRecord;
import java.math.BigDecimal;
import java.util.List;

/**
 * 竞拍记录业务接口
 */
public interface AuctionRecordService {
    
    /**
     * 提交竞拍
     * @param auctionId 拍卖品ID
     * @param userId 用户ID
     * @param price 竞拍价格
     * @return 是否竞拍成功
     */
    boolean bid(Long auctionId, Long userId, BigDecimal price);
    
    /**
     * 根据拍卖品ID查询竞拍记录
     * @param auctionId 拍卖品ID
     * @return 竞拍记录列表
     */
    List<AuctionRecord> getRecordsByAuctionId(Long auctionId);
    
    /**
     * 根据用户ID查询竞拍记录
     * @param userId 用户ID
     * @return 竞拍记录列表
     */
    List<AuctionRecord> getRecordsByUserId(Long userId);
    
    /**
     * 查询拍卖品的最高竞拍记录
     * @param auctionId 拍卖品ID
     * @return 最高竞拍记录，如果没有竞拍记录，返回null
     */
    AuctionRecord getHighestRecord(Long auctionId);
    
    /**
     * 查询拍卖品的所有竞拍记录并按价格降序排序
     * @param auctionId 拍卖品ID
     * @return 竞拍记录列表
     */
    List<AuctionRecord> getRecordsSortedByPrice(Long auctionId);
} 