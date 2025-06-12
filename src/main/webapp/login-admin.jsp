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
    <title>管理员登录 - 在线拍卖系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h2>管理员登录</h2>
        
        <% if(request.getAttribute("errorMsg") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errorMsg") %>
            </div>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/user/admin" method="post">
            <div class="form-group">
                <label for="userName">管理员账号:</label>
                <input type="text" id="userName" name="userName" required>
            </div>
            
            <div class="form-group">
                <label for="userPassword">密码:</label>
                <input type="password" id="userPassword" name="userPassword" required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">登录</button>
                <a href="${pageContext.request.contextPath}/user/login" class="btn btn-link">普通用户登录</a>
            </div>
        </form>
    </div>
</body>
</html>
