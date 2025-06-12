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
    <title>${auction.auctionName} - 在线拍卖系统</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <div class="container">
        <%-- 引入页面头部 --%>
        <jsp:include page="header.jsp" />
        
        <% if(request.getAttribute("errorMsg") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errorMsg") %>
            </div>
        <% } %>
        
        <div class="auction-detail">
            <div class="auction-detail-image">
                <img src="<%=request.getContextPath()%>/upload/${auction.auctionPic}" alt="${auction.auctionName}">
            </div>
            <div class="auction-detail-info">
                <h2>${auction.auctionName}</h2>
                <p><strong>起拍价:</strong> ￥<fmt:formatNumber value="${auction.auctionStartPrice}" pattern="#,##0.00"/></p>
                <p><strong>底价:</strong> ￥<fmt:formatNumber value="${auction.auctionUpset}" pattern="#,##0.00"/></p>
                <p><strong>开始时间:</strong> <fmt:formatDate value="${auction.auctionStartTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                <p><strong>结束时间:</strong> <fmt:formatDate value="${auction.auctionEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                <p><strong>状态:</strong> ${isFinished ? '已结束' : '正在进行'}</p>
                
                <c:if test="${highestRecord != null}">
                    <p><strong>当前最高价:</strong> ￥<fmt:formatNumber value="${highestRecord.auctionPrice}" pattern="#,##0.00"/></p>
                </c:if>
                
                <div class="auction-description">
                    <h3>拍卖品描述</h3>
                    <p>${auction.auctionDesc}</p>
                </div>
                
                <c:if test="${!isFinished && sessionScope.user != null && !sessionScope.user.userIsAdmin}">
                    <div class="bid-form">
                        <h3>参与竞拍</h3>
                        <form action="<%=request.getContextPath()%>/auction/bid" method="post">
                            <input type="hidden" name="auctionId" value="${auction.auctionId}">
                            <div class="form-group">
                                <label for="price">竞拍价格 (￥):</label>
                                <input type="number" id="price" name="price" step="0.01" min="${highestRecord != null ? highestRecord.auctionPrice + 1 : auction.auctionStartPrice}" required>
                                <small>最低出价: ￥${highestRecord != null ? highestRecord.auctionPrice + 1 : auction.auctionStartPrice}</small>
                            </div>
                            <button type="submit" class="btn btn-primary">提交竞拍</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
        
        <div class="auction-records">
            <h3>竞拍记录</h3>
            <c:if test="${empty records}">
                <p class="no-data">暂无竞拍记录</p>
            </c:if>
            
            <c:if test="${!empty records}">
                <table class="record-table">
                    <thead>
                        <tr>
                            <th>竞拍者</th>
                            <th>竞拍价格</th>
                            <th>竞拍时间</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${records}" var="record">
                            <tr>
                                <td>${record.userId}</td>
                                <td>￥<fmt:formatNumber value="${record.auctionPrice}" pattern="#,##0.00"/></td>
                                <td><fmt:formatDate value="${record.auctionTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        
        <%-- 管理员操作 --%>
        <c:if test="${sessionScope.user != null && sessionScope.user.userIsAdmin}">
            <div class="admin-actions">
                <a href="<%=request.getContextPath()%>/auction/update?id=${auction.auctionId}" class="btn btn-primary">编辑拍卖品</a>
                <a href="<%=request.getContextPath()%>/auction/delete?id=${auction.auctionId}" class="btn btn-danger" onclick="return confirm('确定要删除这个拍卖品吗？')">删除拍卖品</a>
            </div>
        </c:if>
        
        <div class="back-link">
            <a href="<%=request.getContextPath()%>/auction/list">返回拍卖品列表</a>
        </div>
    </div>
</body>
</html>
