package cn.wameeee.service.impl;

import cn.wameeee.entity.Auction;
import cn.wameeee.mapper.AuctionMapper;
import cn.wameeee.service.AuctionService;
import cn.wameeee.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 拍卖品业务实现类
 */
public class AuctionServiceImpl implements AuctionService {

    /**
     * 根据ID查询拍卖品
     * @param auctionId 拍卖品ID
     * @return 拍卖品对象
     */
    @Override
    public Auction getAuctionById(Long auctionId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            return mapper.selectById(auctionId);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询所有拍卖品
     * @return 拍卖品列表
     */
    @Override
    public List<Auction> getAllAuctions() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            return mapper.selectAll();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询正在进行中的拍卖品
     * @return 拍卖品列表
     */
    @Override
    public List<Auction> getOngoingAuctions() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            return mapper.selectOngoing();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询已结束的拍卖品
     * @return 拍卖品列表
     */
    @Override
    public List<Auction> getFinishedAuctions() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            return mapper.selectFinished();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据名称模糊查询拍卖品
     * @param auctionName 拍卖品名称
     * @return 拍卖品列表
     */
    @Override
    public List<Auction> getAuctionsByName(String auctionName) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            return mapper.selectByName(auctionName);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 添加拍卖品
     * @param auction 拍卖品对象
     * @return 是否添加成功
     */
    @Override
    public boolean addAuction(Auction auction) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            int rows = mapper.insert(auction);
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
     * 更新拍卖品
     * @param auction 拍卖品对象
     * @return 是否更新成功
     */
    @Override
    public boolean updateAuction(Auction auction) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            int rows = mapper.update(auction);
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
     * 删除拍卖品
     * @param auctionId 拍卖品ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAuction(Long auctionId) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        try {
            AuctionMapper mapper = sqlSession.getMapper(AuctionMapper.class);
            int rows = mapper.delete(auctionId);
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