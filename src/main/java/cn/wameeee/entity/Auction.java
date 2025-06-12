package cn.wameeee.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Auction {
    private Long auctionId;
    private String auctionName;
    private BigDecimal auctionStartPrice;
    private BigDecimal auctionUpset;
    private Date auctionStartTime;
    private Date auctionEndTime;
    private String auctionPic;
    private String auctionPicType;
    private String auctionDesc;

    public Auction() {
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public BigDecimal getAuctionStartPrice() {
        return auctionStartPrice;
    }

    public void setAuctionStartPrice(BigDecimal auctionStartPrice) {
        this.auctionStartPrice = auctionStartPrice;
    }

    public BigDecimal getAuctionUpset() {
        return auctionUpset;
    }

    public void setAuctionUpset(BigDecimal auctionUpset) {
        this.auctionUpset = auctionUpset;
    }

    public Date getAuctionStartTime() {
        return auctionStartTime;
    }

    public void setAuctionStartTime(Date auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public Date getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(Date auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public String getAuctionPic() {
        return auctionPic;
    }

    public void setAuctionPic(String auctionPic) {
        this.auctionPic = auctionPic;
    }

    public String getAuctionPicType() {
        return auctionPicType;
    }

    public void setAuctionPicType(String auctionPicType) {
        this.auctionPicType = auctionPicType;
    }

    public String getAuctionDesc() {
        return auctionDesc;
    }

    public void setAuctionDesc(String auctionDesc) {
        this.auctionDesc = auctionDesc;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", auctionName='" + auctionName + '\'' +
                ", auctionStartPrice=" + auctionStartPrice +
                ", auctionUpset=" + auctionUpset +
                ", auctionStartTime=" + auctionStartTime +
                ", auctionEndTime=" + auctionEndTime +
                ", auctionPic='" + auctionPic + '\'' +
                ", auctionPicType='" + auctionPicType + '\'' +
                ", auctionDesc='" + auctionDesc + '\'' +
                '}';
    }
} 