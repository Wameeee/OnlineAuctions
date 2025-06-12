<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="site-header">
    <div class="logo">
        <a href="${pageContext.request.contextPath}/index.jsp">在线拍卖系统</a>
    </div>
    
    <nav class="main-nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/auction/list">所有拍卖品</a></li>
            <li><a href="${pageContext.request.contextPath}/auction/ongoing">正在进行</a></li>
            <li><a href="${pageContext.request.contextPath}/auction/finished">已结束</a></li>
        </ul>
    </nav>
    
    <div class="user-info">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <span>欢迎，${sessionScope.user.userName}</span>
                <c:if test="${sessionScope.user.userIsAdmin}">
                    <span class="admin-badge">管理员</span>
                </c:if>
                <div class="user-actions">
                    <a href="${pageContext.request.contextPath}/user/profile">个人资料</a>
                    <c:if test="${sessionScope.user.userIsAdmin}">
                        <a href="${pageContext.request.contextPath}/user/list">用户管理</a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="user-actions">
                    <a href="${pageContext.request.contextPath}/user/login">登录</a>
                    <a href="${pageContext.request.contextPath}/user/register">注册</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header> 