# 在线拍卖系统 (Online Auction System)

## 项目概述

本项目是一个基于 Java Web 技术开发的在线拍卖系统，旨在提供一个用户友好、功能完善的在线拍卖平台。系统支持用户注册、商品浏览、拍卖出价、拍卖管理等核心功能。

## 系统功能

### 用户功能

- 用户注册与登录
- 个人信息管理
- 浏览拍卖商品
- 参与商品竞拍
- 查看竞拍历史记录
- 密码找回

### 管理员功能

- 拍卖商品管理（添加、修改、删除）
- 用户管理
- 拍卖记录管理
- 系统设置

## 技术栈

- **后端技术**：

  - Java 8
  - Servlet/JSP
  - MyBatis
  - MySQL 数据库
  - 自定义请求处理框架

- **前端技术**：

  - HTML/CSS/JavaScript
  - JSP/JSTL

- **开发工具**：
  - Maven
  - Tomcat 9

## 数据库设计

系统包含以下主要数据表：

1. `auction` - 拍卖品信息表
2. `auctionUser` - 用户信息表
3. `auctionRecord` - 竞拍记录表

## 环境要求

- JDK 1.8 或更高版本
- Maven 3.6 或更高版本
- MySQL 5.5 或更高版本
- Tomcat 9.0 或更高版本

## 项目配置与部署

### 数据库配置

1. 创建数据库并执行 SQL 脚本：

   ```sql
   source create_auction.sql
   ```

2. 配置数据库连接参数（位于 `src/main/resources/` 目录下的配置文件）

### 项目构建

```bash
# 打包项目
mvn clean package

# 部署到本地Tomcat
mvn tomcat7:run
```

### 访问项目

成功部署后，可通过以下 URL 访问系统：

```
http://localhost:8080/auction
```

## 默认管理员账号

- 用户名：admin
- 密码：admin123

## 项目结构

```
OnlineAuctions/
├── src/
│   ├── main/
│   │   ├── java/        # Java源代码
│   │   ├── resources/   # 配置文件
│   │   └── webapp/      # Web资源
├── pom.xml              # Maven配置文件
└── create_auction.sql   # 数据库脚本
```

## 注意事项

- 首次使用请先执行数据库脚本创建必要的表结构
- 默认端口为 8080，可在 Maven Tomcat 插件配置中修改
