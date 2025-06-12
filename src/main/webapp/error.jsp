<%--
  Created by IntelliJ IDEA.
  User: wameeee
  Date: 2025/6/10
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>错误 - 在线拍卖系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .error-container {
            text-align: center;
            padding: 50px 20px;
            max-width: 600px;
            margin: 0 auto;
        }
        .error-code {
            font-size: 72px;
            color: #e74c3c;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 24px;
            margin-bottom: 30px;
        }
        .error-details {
            color: #777;
            margin-bottom: 30px;
            padding: 15px;
            background: #f8f8f8;
            border-radius: 5px;
            text-align: left;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">
            <% 
            Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
            if (statusCode != null) {
                out.print(statusCode);
            } else {
                out.print("错误");
            }
            %>
        </div>
        
        <div class="error-message">
            <% 
            String errorMessage = (String)request.getAttribute("javax.servlet.error.message");
            if (errorMessage != null && !errorMessage.isEmpty()) {
                out.print(errorMessage);
            } else {
                out.print("抱歉，系统发生了错误");
            }
            %>
        </div>
        
        <% if (exception != null) { %>
        <div class="error-details">
            错误详情: <%= exception.getMessage() %>
        </div>
        <% } %>
        
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn primary">返回首页</a>
            <a href="javascript:history.back();" class="btn">返回上一页</a>
        </div>
    </div>
</body>
</html>
