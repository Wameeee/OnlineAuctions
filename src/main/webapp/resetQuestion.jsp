<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>密码重置 - 在线拍卖系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <h1>密码重置</h1>
        <p>第二步：回答安全问题并设置新密码</p>
        
        <c:if test="${not empty errorMsg}">
            <div class="error-message">${errorMsg}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/user/reset-question" method="post">
            <input type="hidden" name="userName" value="${userName}" />
            
            <div class="form-group">
                <label>安全问题:</label>
                <input type="text" value="${userQuestion}" readonly />
            </div>
            
            <div class="form-group">
                <label>安全问题答案:</label>
                <input type="text" name="userAnswer" required />
            </div>
            
            <div class="form-group">
                <label>新密码:</label>
                <input type="password" name="newPassword" required />
            </div>
            
            <div class="form-group">
                <label>确认新密码:</label>
                <input type="password" name="confirmPassword" required />
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn primary">重置密码</button>
                <a href="${pageContext.request.contextPath}/user/reset-password" class="btn">返回</a>
            </div>
        </form>
    </div>
</body>
</html> 