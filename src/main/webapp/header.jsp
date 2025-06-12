<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="site-header">
    <div class="logo">
        <a href="<%=request.getContextPath()%>/index.jsp">在线拍卖系统</a>
    </div>
    
    <nav class="main-nav">
        <ul>
            <li><a href="<%=request.getContextPath()%>/auction/list">所有拍卖品</a></li>
            <li><a href="<%=request.getContextPath()%>/auction/ongoing">正在进行</a></li>
            <li><a href="<%=request.getContextPath()%>/auction/finished">已结束</a></li>
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
                    <a href="<%=request.getContextPath()%>/user/profile">个人资料</a>
                    <c:if test="${sessionScope.user.userIsAdmin}">
                        <a href="<%=request.getContextPath()%>/user/list">用户管理</a>
                    </c:if>
                    <a href="<%=request.getContextPath()%>/user/logout">退出登录</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="user-actions">
                    <a href="<%=request.getContextPath()%>/user/login">登录</a>
                    <a href="<%=request.getContextPath()%>/user/register">注册</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header> 