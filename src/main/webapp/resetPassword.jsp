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
        <p>第一步：请输入您的用户名</p>
        
        <c:if test="${not empty errorMsg}">
            <div class="error-message">${errorMsg}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/user/reset-password" method="post">
            <div class="form-group">
                <label>用户名:</label>
                <input type="text" name="userName" required />
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn primary">下一步</button>
                <a href="${pageContext.request.contextPath}/user/login" class="btn">返回登录</a>
            </div>
        </form>
    </div>
</body>
</html> 