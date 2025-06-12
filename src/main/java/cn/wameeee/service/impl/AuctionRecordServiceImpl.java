package cn.wameeee.service.impl;

import cn.wameeee.entity.Auction;
import cn.wameeee.entity.AuctionRecord;
import cn.wameeee.entity.AuctionUser;
import cn.wameeee.mapper.AuctionMapper;
import cn.wameeee.mapper.AuctionRecordMapper;
import cn.wameeee.mapper.AuctionUserMapper;
import cn.wameeee.service.AuctionRecordService;
import cn.wameeee.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 竞拍记录业务实现类
 */
public class AuctionRecordServiceImpl implements AuctionRecordService {

    /**
     * 提交竞拍
     * @param auctionId 拍卖品ID
     * @param userId 用户ID
     * @param price 竞拍价格
     * @return 是否竞拍成功
     */
    @Override
    public boolean bid(Long auctionId, Long userId, BigDecimal price) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            // 检查拍卖品是否存在
            AuctionMapper auctionMapper = sqlSession.getMapper(AuctionMapper.class);
            Auction auction = auctionMapper.selectById(auctionId);
            if (auction == null) {
                return false;
            }
            
            // 检查拍卖是否已结束
            Date now = new Date();
            if (now.after(auction.getAuctionEndTime()) || now.before(auction.getAuctionStartTime())) {
                return false;
            }
            
            // 检查用户是否存在
            AuctionUserMapper userMapper = sqlSession.getMapper(AuctionUserMapper.class);
            AuctionUser user = userMapper.selectById(userId);
            if (user == null) {
                return false;
            }
            
            // 检查竞拍价格是否高于起拍价
            if (price.compareTo(auction.getAuctionStartPrice()) < 0) {
                return false;
            }
            
            // 检查是否高于当前最高价
            AuctionRecordMapper recordMapper = sqlSession.getMapper(AuctionRecordMapper.class);
            AuctionRecord highestRecord = recordMapper.selectHighestRecord(auctionId);
            if (highestRecord != null && price.compareTo(highestRecord.getAuctionPrice()) <= 0) {
                return false;
            }
            
            // 创建竞拍记录
            AuctionRecord record = new AuctionRecord();
            record.setAuctionId(auctionId);
            record.setUserId(userId);
            record.setAuctionPrice(price);
            record.setAuctionTime(now);
            
            int rows = recordMapper.insert(record);
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
     * 根据拍卖品ID查询竞拍记录
     * @param auctionId 拍卖品ID
     * @return 竞拍记录列表
     */
    @Override
    public List<AuctionRecord> getRecordsByAuctionId(Long auctionId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionRecordMapper mapper = sqlSession.getMapper(AuctionRecordMapper.class);
            return mapper.selectByAuctionId(auctionId);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据用户ID查询竞拍记录
     * @param userId 用户ID
     * @return 竞拍记录列表
     */
    @Override
    public List<AuctionRecord> getRecordsByUserId(Long userId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionRecordMapper mapper = sqlSession.getMapper(AuctionRecordMapper.class);
            return mapper.selectByUserId(userId);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询拍卖品的最高竞拍记录
     * @param auctionId 拍卖品ID
     * @return 最高竞拍记录，如果没有竞拍记录，返回null
     */
    @Override
    public AuctionRecord getHighestRecord(Long auctionId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionRecordMapper mapper = sqlSession.getMapper(AuctionRecordMapper.class);
            return mapper.selectHighestRecord(auctionId);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询拍卖品的所有竞拍记录并按价格降序排序
     * @param auctionId 拍卖品ID
     * @return 竞拍记录列表
     */
    @Override
    public List<AuctionRecord> getRecordsSortedByPrice(Long auctionId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionRecordMapper mapper = sqlSession.getMapper(AuctionRecordMapper.class);
            return mapper.selectSortedByPrice(auctionId);
        } finally {
            sqlSession.close();
        }
    }
} 