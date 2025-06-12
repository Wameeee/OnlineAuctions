<%--
  Created by IntelliJ IDEA.
  User: wameeee
  Date: 2025/6/10
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>拍卖品列表 - 在线拍卖系统</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <div class="container">
        <%-- 引入页面头部 --%>
        <jsp:include page="header.jsp" />
        
        <h2>
            <c:choose>
                <c:when test="${auctionStatus == 'ongoing'}">正在进行的拍卖</c:when>
                <c:when test="${auctionStatus == 'finished'}">已结束的拍卖</c:when>
                <c:otherwise>所有拍卖品</c:otherwise>
            </c:choose>
        </h2>
        
        <%-- 搜索框 --%>
        <div class="search-box">
            <form action="<%=request.getContextPath()%>/auction/search" method="get">
                <input type="text" name="keyword" placeholder="输入拍卖品名称..." value="${keyword}">
                <button type="submit">搜索</button>
            </form>
        </div>
        
        <%-- 筛选链接 --%>
        <div class="filter-links">
            <a href="<%=request.getContextPath()%>/auction/list">所有拍卖品</a> |
            <a href="<%=request.getContextPath()%>/auction/ongoing">正在进行</a> |
            <a href="<%=request.getContextPath()%>/auction/finished">已结束</a>
        </div>
        
        <%-- 拍卖品列表 --%>
        <div class="auction-list">
            <c:if test="${empty auctions}">
                <p class="no-data">暂无拍卖品</p>
            </c:if>
            
            <c:forEach items="${auctions}" var="auction">
                <div class="auction-item">
                    <div class="auction-image">
                        <img src="<%=request.getContextPath()%>/upload/${auction.auctionPic}" alt="${auction.auctionName}">
                    </div>
                    <div class="auction-info">
                        <h3><a href="<%=request.getContextPath()%>/auction/detail?id=${auction.auctionId}">${auction.auctionName}</a></h3>
                        <p>起拍价: ￥<fmt:formatNumber value="${auction.auctionStartPrice}" pattern="#,##0.00"/></p>
                        <p>底价: ￥<fmt:formatNumber value="${auction.auctionUpset}" pattern="#,##0.00"/></p>
                        <p>开始时间: <fmt:formatDate value="${auction.auctionStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                        <p>结束时间: <fmt:formatDate value="${auction.auctionEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                        <a href="<%=request.getContextPath()%>/auction/detail?id=${auction.auctionId}" class="btn btn-primary">查看详情</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <%-- 管理员操作 --%>
        <c:if test="${sessionScope.user != null && sessionScope.user.userIsAdmin}">
            <div class="admin-actions">
                <a href="<%=request.getContextPath()%>/auction/add" class="btn btn-success">添加拍卖品</a>
            </div>
        </c:if>
    </div>
</body>
</html>
