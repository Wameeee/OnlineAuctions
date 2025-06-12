package cn.wameeee.web.filter;

import cn.wameeee.entity.AuctionUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 权限检查过滤器
 */
@WebFilter(urlPatterns = {"/user/*", "/auction/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        // 获取请求路径
        String path = req.getRequestURI().substring(req.getContextPath().length());
        
        // 不需要登录就能访问的路径
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // 检查用户是否登录
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }
        
        // 检查是否需要管理员权限
        if (isAdminPath(path)) {
            AuctionUser user = (AuctionUser) session.getAttribute("user");
            if (!user.getUserIsAdmin()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "需要管理员权限");
                return;
            }
        }
        
        // 继续请求处理
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 销毁操作
    }
    
    /**
     * 判断是否是公开访问路径
     */
    private boolean isPublicPath(String path) {
        return path.startsWith("/user/login") || 
               path.startsWith("/user/register") || 
               path.startsWith("/user/admin") ||
               path.startsWith("/auction/list") || 
               path.startsWith("/auction/detail") ||
               path.startsWith("/auction/search") ||
               path.endsWith(".css") || 
               path.endsWith(".js") || 
               path.endsWith(".jpg") || 
               path.endsWith(".png") || 
               path.endsWith(".gif");
    }
    
    /**
     * 判断是否是需要管理员权限的路径
     */
    private boolean isAdminPath(String path) {
        return path.startsWith("/user/list") || 
               path.startsWith("/user/delete") ||
               path.startsWith("/auction/add") || 
               path.startsWith("/auction/update") || 
               path.startsWith("/auction/delete");
    }
} 