/*
* SQLyog 企业版 - MySQL GUI v8.14 
* MySQL - 5.5.40 : Database - OnlineAuctionSystem
*/


SET NAMES utf8;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`OnlineAuctionSystem` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE `OnlineAuctionSystem`;

SET FOREIGN_KEY_CHECKS=0;

/*Table structure for table `auction` */

drop table if exists `auction`;

create table `auction` (
   `auctionId`            bigint(11)      not null    AUTO_INCREMENT  comment '拍卖品编号',
   `auctionName`          varchar(100)    not null    comment '拍卖品名称',
   `auctionStartPrice`    decimal(9,2)    not null    comment '拍卖品起拍价',
   `auctionUpset`         decimal(9,2)    not null    comment '拍卖品底价',
   `auctionStartTime`     datetime        not null    comment '拍卖品拍卖开始时间',
   `auctionEndTime`       datetime        not null    comment '拍卖品拍卖结束时间',
   `auctionPic`           varchar(255)    not null    comment '拍卖品图片路径',
   `auctionPicType`       varchar(20)     not null    comment '拍卖品图片扩展名',
   `auctionDesc`          varchar(500)    comment '拍卖品描述',
   primary key (`auctionId`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COMMENT='拍卖品';

/*Table structure for table `auctionUser` */

drop table if exists `auctionUser`;

create table `auctionUser` (
   `userId`             bigint(11)      not null    AUTO_INCREMENT    comment '用户编号',
   `userName`           varchar(40)     unique      not null    comment '用户名',
   `userPassword`       varchar(20)     not null    comment '用户密码',
   `userCardNo`         varchar(20)     comment '用户身份证号',
   `userTel`            varchar(20)     comment '用户电话',
   `userAddress`        varchar(400)    comment '用户地址',
   `userPostNumber`     varchar(6)      comment '用户邮政编号',
   `userIsAdmin`        bit(1)          default 0    comment '用户是否是管理员，0是竞拍者，1是管理员，默认值是0',
   `userQuestion`       varchar(200)    comment '用户找回密码的问题',
   `userAnswer`         varchar(200)    comment '用户找回密码答案',
   primary key (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Table structure for table `auctionRecord` */

drop table if exists `auctionRecord`;

create table `auctionRecord` (
   `id`             bigint(11)      not null    AUTO_INCREMENT  comment '竞拍记录编号',
   `userId`         bigint(11)      comment '竞拍者编号',
   `auctionId`      bigint(11)      not null    comment '拍卖品编号',
   `auctionTime`    timestamp       default NOW()   not null    comment '竞价时间',
   `auctionPrice`   decimal(9,2)    not null    comment '竞拍价格',
   primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8 COMMENT='竞拍记录';

alter table `auctionRecord` add constraint `FK_rec_REF_user`
    foreign key (`userId`) references `auctionUser`(`userId`);
         
alter table `auctionRecord` add constraint `FK_rec_REF_AUC`
    foreign key (`auctionId`) references `auction`(`auctionId`);

