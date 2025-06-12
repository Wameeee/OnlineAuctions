<%--
  Created by IntelliJ IDEA.
  User: wameeee
  Date: 2025/6/10
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录 - 在线拍卖系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>用户登录</h2>
        
        <c:if test="${not empty errorMsg}">
            <div class="error-message">
                ${errorMsg}
            </div>
        </c:if>
        
        <c:if test="${not empty successMsg}">
            <div class="success-message">
                ${successMsg}
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/user/login" method="post">
            <div class="form-group">
                <label for="userName">用户名:</label>
                <input type="text" id="userName" name="userName" required>
            </div>
            
            <div class="form-group">
                <label for="userPassword">密码:</label>
                <input type="password" id="userPassword" name="userPassword" required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">登录</button>
                <a href="${pageContext.request.contextPath}/user/register" class="btn btn-link">注册新用户</a>
                <a href="${pageContext.request.contextPath}/user/admin" class="btn btn-link">管理员登录</a>
                <a href="${pageContext.request.contextPath}/user/reset-password" class="btn btn-link">忘记密码?</a>
            </div>
        </form>
    </div>
</body>
</html>
