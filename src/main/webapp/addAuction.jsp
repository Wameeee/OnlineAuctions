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
    <title>添加拍卖品 - 在线拍卖系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <c:if test="${auction != null}">
        <fmt:formatDate value="${auction.auctionStartTime}" pattern="yyyy-MM-dd'T'HH:mm" var="formattedStartTime" />
        <fmt:formatDate value="${auction.auctionEndTime}" pattern="yyyy-MM-dd'T'HH:mm" var="formattedEndTime" />
    </c:if>
</head>
<body>
    <div class="container">
        <%-- 引入页面头部 --%>
        <jsp:include page="header.jsp" />
        
        <h2>${auction != null ? '编辑拍卖品' : '添加拍卖品'}</h2>
        
        <% if(request.getAttribute("errorMsg") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errorMsg") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}${auction != null ? '/auction/update' : '/auction/add'}" method="post" enctype="multipart/form-data">
            <c:if test="${auction != null}">
                <input type="hidden" name="auctionId" value="${auction.auctionId}">
            </c:if>
            
            <div class="form-group">
                <label for="auctionName">拍卖品名称:</label>
                <input type="text" id="auctionName" name="auctionName" value="${auction != null ? auction.auctionName : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="auctionStartPrice">起拍价:</label>
                <input type="number" id="auctionStartPrice" name="auctionStartPrice" step="0.01" min="0" value="${auction != null ? auction.auctionStartPrice : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="auctionUpset">底价:</label>
                <input type="number" id="auctionUpset" name="auctionUpset" step="0.01" min="0" value="${auction != null ? auction.auctionUpset : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="auctionStartTime">开始时间:</label>
                <input type="datetime-local" id="auctionStartTime" name="auctionStartTime" value="${formattedStartTime}" required>
            </div>
            
            <div class="form-group">
                <label for="auctionEndTime">结束时间:</label>
                <input type="datetime-local" id="auctionEndTime" name="auctionEndTime" value="${formattedEndTime}" required>
            </div>
            
            <div class="form-group">
                <label for="auctionPic">拍卖品图片:</label>
                <input type="file" id="auctionPic" name="auctionPic" accept="image/*" ${auction == null ? 'required' : ''}>
                <c:if test="${auction != null && auction.auctionPic != null}">
                    <p>当前图片: ${auction.auctionPic}</p>
                    <p>如果不上传新图片，将保留原图片</p>
                </c:if>
            </div>
            
            <div class="form-group">
                <label for="auctionDesc">拍卖品描述:</label>
                <textarea id="auctionDesc" name="auctionDesc" rows="5">${auction != null ? auction.auctionDesc : ''}</textarea>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${auction != null ? '更新' : '添加'}</button>
                <a href="${pageContext.request.contextPath}/auction/list" class="btn btn-link">取消</a>
            </div>
        </form>
    </div>
</body>
</html>
