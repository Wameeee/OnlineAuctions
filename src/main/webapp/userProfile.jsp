<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人资料 - 在线拍卖系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <h1>个人资料</h1>
        
        <c:if test="${not empty errorMsg}">
            <div class="error-message">${errorMsg}</div>
        </c:if>
        
        <c:if test="${not empty successMsg}">
            <div class="success-message">${successMsg}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/user/update" method="post">
            <input type="hidden" name="userId" value="${user.userId}" />
            
            <div class="form-group">
                <label>用户名:</label>
                <input type="text" value="${user.userName}" readonly />
            </div>
            
            <div class="form-group">
                <label>密码:</label>
                <input type="password" name="userPassword" placeholder="留空表示不修改" />
            </div>
            
            <div class="form-group">
                <label>身份证号:</label>
                <input type="text" name="userCardNo" value="${user.userCardNo}" />
            </div>
            
            <div class="form-group">
                <label>电话:</label>
                <input type="text" name="userTel" value="${user.userTel}" />
            </div>
            
            <div class="form-group">
                <label>地址:</label>
                <textarea name="userAddress">${user.userAddress}</textarea>
            </div>
            
            <div class="form-group">
                <label>邮政编码:</label>
                <input type="text" name="userPostNumber" value="${user.userPostNumber}" />
            </div>
            
            <div class="form-group">
                <label>密保问题:</label>
                <input type="text" name="userQuestion" value="${user.userQuestion}" />
            </div>
            
            <div class="form-group">
                <label>密保答案:</label>
                <input type="text" name="userAnswer" value="${user.userAnswer}" />
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn primary">保存修改</button>
                <a href="${pageContext.request.contextPath}/auction/list" class="btn">返回</a>
            </div>
        </form>
    </div>
</body>
</html> 