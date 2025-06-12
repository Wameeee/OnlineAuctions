package cn.wameeee.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AuctionRecord {
    private Long id;
    private Long userId;
    private Long auctionId;
    private Date auctionTime;
    private BigDecimal auctionPrice;
    
    // 关联的实体对象
    private AuctionUser auctionUser;
    private Auction auction;

    public AuctionRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Date getAuctionTime() {
        return auctionTime;
    }

    public void setAuctionTime(Date auctionTime) {
        this.auctionTime = auctionTime;
    }

    public BigDecimal getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(BigDecimal auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public AuctionUser getAuctionUser() {
        return auctionUser;
    }

    public void setAuctionUser(AuctionUser auctionUser) {
        this.auctionUser = auctionUser;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    @Override
    public String toString() {
        return "AuctionRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", auctionId=" + auctionId +
                ", auctionTime=" + auctionTime +
                ", auctionPrice=" + auctionPrice +
                '}';
    }
} 