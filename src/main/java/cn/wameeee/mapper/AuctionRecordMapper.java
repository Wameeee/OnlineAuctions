package cn.wameeee.mapper;

import cn.wameeee.entity.AuctionRecord;
import java.util.List;

/**
 * AuctionRecord的Mapper接口
 */
public interface AuctionRecordMapper {
    
    /**
     * 根据ID查询竞拍记录
     * @param id 竞拍记录ID
     * @return 竞拍记录对象
     */
    AuctionRecord selectById(Long id);
    
    /**
     * 查询所有竞拍记录
     * @return 竞拍记录列表
     */
    List<AuctionRecord> selectAll();
    
    /**
     * 根据拍卖品ID查询竞拍记录
     * @param auctionId 拍卖品ID
     * @return 竞拍记录列表
     */
    List<AuctionRecord> selectByAuctionId(Long auctionId);
    
    /**
     * 根据用户ID查询竞拍记录
     * @param userId 用户ID
     * @return 竞拍记录列表
     */
    List<AuctionRecord> selectByUserId(Long userId);
    
    /**
     * 查询拍卖品的最高竞价记录
     * @param auctionId 拍卖品ID
     * @return 竞拍记录对象
     */
    AuctionRecord selectMaxPriceRecord(Long auctionId);
    
    /**
     * 查询拍卖品的最高竞价记录
     * @param auctionId 拍卖品ID
     * @return 竞拍记录对象
     */
    AuctionRecord selectHighestRecord(Long auctionId);
    
    /**
     * 查询拍卖品的所有竞拍记录并按价格降序排序
     * @param auctionId 拍卖品ID
     * @return 竞拍记录列表
     */
    List<AuctionRecord> selectSortedByPrice(Long auctionId);
    
    /**
     * 插入竞拍记录
     * @param record 竞拍记录对象
     * @return 影响的行数
     */
    int insert(AuctionRecord record);
    
    /**
     * 更新竞拍记录
     * @param record 竞拍记录对象
     * @return 影响的行数
     */
    int update(AuctionRecord record);
    
    /**
     * 删除竞拍记录
     * @param id 竞拍记录ID
     * @return 影响的行数
     */
    int delete(Long id);
    
    /**
     * 删除拍卖品的所有竞拍记录
     * @param auctionId 拍卖品ID
     * @return 影响的行数
     */
    int deleteByAuctionId(Long auctionId);
} 