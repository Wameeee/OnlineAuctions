<%--
  Created by IntelliJ IDEA.
  User: wameeee
  Date: 2025/6/10
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册 - 在线拍卖系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>用户注册</h2>
        
        <% if(request.getAttribute("errorMsg") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errorMsg") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/user/register" method="post">
            <div class="form-group">
                <label for="userName">用户名:</label>
                <input type="text" id="userName" name="userName" required>
                <small>用户名将用于登录，请确保唯一性</small>
            </div>
            
            <div class="form-group">
                <label for="userPassword">密码:</label>
                <input type="password" id="userPassword" name="userPassword" required>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">确认密码:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            
            <div class="form-group">
                <label for="userCardNo">身份证号:</label>
                <input type="text" id="userCardNo" name="userCardNo">
            </div>
            
            <div class="form-group">
                <label for="userTel">电话:</label>
                <input type="tel" id="userTel" name="userTel">
            </div>
            
            <div class="form-group">
                <label for="userAddress">地址:</label>
                <input type="text" id="userAddress" name="userAddress">
            </div>
            
            <div class="form-group">
                <label for="userPostNumber">邮政编码:</label>
                <input type="text" id="userPostNumber" name="userPostNumber" maxlength="6">
            </div>
            
            <div class="form-group">
                <label for="userQuestion">密码找回问题:</label>
                <input type="text" id="userQuestion" name="userQuestion">
            </div>
            
            <div class="form-group">
                <label for="userAnswer">密码找回答案:</label>
                <input type="text" id="userAnswer" name="userAnswer">
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">注册</button>
                <a href="${pageContext.request.contextPath}/user/login" class="btn btn-link">已有账号？立即登录</a>
            </div>
        </form>
    </div>
</body>
</html>
