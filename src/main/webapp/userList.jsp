<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理 - 在线拍卖系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <h1>用户管理</h1>
        
        <c:if test="${not empty errorMsg}">
            <div class="error-message">${errorMsg}</div>
        </c:if>
        
        <c:if test="${not empty successMsg}">
            <div class="success-message">${successMsg}</div>
        </c:if>
        
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>用户名</th>
                    <th>身份证号</th>
                    <th>电话</th>
                    <th>地址</th>
                    <th>邮政编码</th>
                    <th>用户类型</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userName}</td>
                        <td>${user.userCardNo}</td>
                        <td>${user.userTel}</td>
                        <td>${user.userAddress}</td>
                        <td>${user.userPostNumber}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.userIsAdmin}">管理员</c:when>
                                <c:otherwise>普通用户</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${!user.userIsAdmin || sessionScope.user.userId == user.userId}">
                                <a href="javascript:void(0);" onclick="confirmDelete('${user.userId}')" class="btn danger small">删除</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <div class="actions">
            <a href="${pageContext.request.contextPath}/auction/list" class="btn">返回</a>
        </div>
    </div>
    
    <script>
        function confirmDelete(userId) {
            if (confirm("确定要删除该用户吗？此操作不可撤销。")) {
                window.location.href = "${pageContext.request.contextPath}/user/delete?id=" + userId;
            }
        }
    </script>
</body>
</html> 