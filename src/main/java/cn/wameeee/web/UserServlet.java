package cn.wameeee.web;

import cn.wameeee.entity.AuctionUser;
import cn.wameeee.service.AuctionUserService;
import cn.wameeee.service.impl.AuctionUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 用户相关的Servlet控制器
 */
@WebServlet(urlPatterns = {"/user/*"})
public class UserServlet extends HttpServlet {

    private AuctionUserService userService = new AuctionUserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            path = "/login";
        }

        switch (path) {
            case "/login":
                showLoginForm(req, resp);
                break;
            case "/register":
                showRegisterForm(req, resp);
                break;
            case "/logout":
                logout(req, resp);
                break;
            case "/profile":
                showUserProfile(req, resp);
                break;
            case "/admin":
                showAdminLogin(req, resp);
                break;
            case "/list":
                listUsers(req, resp);
                break;
            case "/reset-password":
                showResetPasswordForm(req, resp);
                break;
            case "/reset-question":
                showResetQuestionForm(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/user/login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            path = "/login";
        }

        switch (path) {
            case "/login":
                login(req, resp);
                break;
            case "/register":
                register(req, resp);
                break;
            case "/update":
                updateUser(req, resp);
                break;
            case "/delete":
                deleteUser(req, resp);
                break;
            case "/admin":
                adminLogin(req, resp);
                break;
            case "/reset-password":
                resetPassword(req, resp);
                break;
            case "/reset-question":
                verifySecurityQuestion(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/user/login");
                break;
        }
    }

    /**
     * 显示登录表单
     */
    private void showLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    /**
     * 显示管理员登录表单
     */
    private void showAdminLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login-admin.jsp").forward(req, resp);
    }

    /**
     * 显示注册表单
     */
    private void showRegisterForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    /**
     * 用户登录
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");

        AuctionUser user = userService.login(userName, userPassword);
        if (user != null) {
            // 登录成功，将用户信息存入Session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/auction/list");
        } else {
            // 登录失败
            req.setAttribute("errorMsg", "用户名或密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    /**
     * 管理员登录
     */
    private void adminLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");

        AuctionUser user = userService.login(userName, userPassword);
        if (user != null && user.getUserIsAdmin()) {
            // 登录成功，将用户信息存入Session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/auction/list");
        } else {
            // 登录失败
            req.setAttribute("errorMsg", "管理员账号或密码错误");
            req.getRequestDispatcher("/login-admin.jsp").forward(req, resp);
        }
    }

    /**
     * 用户注册
     */
    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String confirmPassword = req.getParameter("confirmPassword");
        String userCardNo = req.getParameter("userCardNo");
        String userTel = req.getParameter("userTel");
        String userAddress = req.getParameter("userAddress");
        String userPostNumber = req.getParameter("userPostNumber");
        String userQuestion = req.getParameter("userQuestion");
        String userAnswer = req.getParameter("userAnswer");

        // 验证密码是否一致
        if (!userPassword.equals(confirmPassword)) {
            req.setAttribute("errorMsg", "两次输入的密码不一致");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        // 创建用户对象
        AuctionUser user = new AuctionUser();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserCardNo(userCardNo);
        user.setUserTel(userTel);
        user.setUserAddress(userAddress);
        user.setUserPostNumber(userPostNumber);
        user.setUserIsAdmin(false);
        user.setUserQuestion(userQuestion);
        user.setUserAnswer(userAnswer);

        // 注册用户
        boolean success = userService.register(user);
        if (success) {
            // 注册成功，跳转到登录页面
            req.setAttribute("successMsg", "注册成功，请登录");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            // 注册失败
            req.setAttribute("errorMsg", "注册失败，用户名可能已存在");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }

    /**
     * 用户登出
     */
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/user/login");
    }

    /**
     * 显示用户个人资料
     */
    private void showUserProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否登录
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        // 获取最新的用户信息
        AuctionUser freshUser = userService.getUserById(user.getUserId());
        req.setAttribute("user", freshUser);
        req.getRequestDispatcher("/userProfile.jsp").forward(req, resp);
    }

    /**
     * 更新用户信息
     */
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否登录
        AuctionUser sessionUser = (AuctionUser) req.getSession().getAttribute("user");
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        // 获取表单数据
        Long userId = Long.parseLong(req.getParameter("userId"));
        String userPassword = req.getParameter("userPassword");
        String userCardNo = req.getParameter("userCardNo");
        String userTel = req.getParameter("userTel");
        String userAddress = req.getParameter("userAddress");
        String userPostNumber = req.getParameter("userPostNumber");
        String userQuestion = req.getParameter("userQuestion");
        String userAnswer = req.getParameter("userAnswer");

        // 只允许用户更新自己的信息，或者管理员可以更新任何用户
        if (!sessionUser.getUserId().equals(userId) && !sessionUser.getUserIsAdmin()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 获取原用户信息
        AuctionUser user = userService.getUserById(userId);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/user/profile");
            return;
        }

        // 更新用户信息
        if (userPassword != null && !userPassword.isEmpty()) {
            user.setUserPassword(userPassword);
        }
        user.setUserCardNo(userCardNo);
        user.setUserTel(userTel);
        user.setUserAddress(userAddress);
        user.setUserPostNumber(userPostNumber);
        user.setUserQuestion(userQuestion);
        user.setUserAnswer(userAnswer);

        // 保存用户信息
        boolean success = userService.updateUser(user);
        if (success) {
            // 如果是更新自己的信息，更新Session中的用户信息
            if (sessionUser.getUserId().equals(userId)) {
                req.getSession().setAttribute("user", user);
            }
            resp.sendRedirect(req.getContextPath() + "/user/profile");
        } else {
            req.setAttribute("errorMsg", "更新用户信息失败");
            req.setAttribute("user", user);
            req.getRequestDispatcher("/userProfile.jsp").forward(req, resp);
        }
    }

    /**
     * 删除用户
     */
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否是管理员
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null || !user.getUserIsAdmin()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            Long userId = Long.parseLong(req.getParameter("id"));
            boolean success = userService.deleteUser(userId);
            resp.sendRedirect(req.getContextPath() + "/user/list");
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/user/list");
        }
    }

    /**
     * 显示所有用户列表（管理员功能）
     */
    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否是管理员
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null || !user.getUserIsAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        List<AuctionUser> users = userService.getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/userList.jsp").forward(req, resp);
    }

    /**
     * 显示密码重置表单（第一步：输入用户名）
     */
    private void showResetPasswordForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/resetPassword.jsp").forward(req, resp);
    }

    /**
     * 显示安全问题表单（第二步：回答安全问题）
     */
    private void showResetQuestionForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        if (userName == null || userName.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/user/reset-password");
            return;
        }

        AuctionUser user = userService.getUserByName(userName);
        if (user == null) {
            req.setAttribute("errorMsg", "用户不存在");
            req.getRequestDispatcher("/resetPassword.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("userName", userName);
        req.setAttribute("userQuestion", user.getUserQuestion());
        req.getRequestDispatcher("/resetQuestion.jsp").forward(req, resp);
    }

    /**
     * 验证安全问题答案
     */
    private void verifySecurityQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String answer = req.getParameter("userAnswer");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (userName == null || userName.isEmpty() || answer == null || answer.isEmpty() ||
                newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            req.setAttribute("errorMsg", "所有字段都是必填的");
            req.getRequestDispatcher("/resetQuestion.jsp").forward(req, resp);
            return;
        }

        // 验证新密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("errorMsg", "两次输入的密码不一致");
            req.setAttribute("userName", userName);
            req.getRequestDispatcher("/resetQuestion.jsp").forward(req, resp);
            return;
        }

        // 验证安全问题答案
        AuctionUser user = userService.getUserByName(userName);
        if (user == null || !user.getUserAnswer().equals(answer)) {
            req.setAttribute("errorMsg", "安全问题答案错误");
            req.setAttribute("userName", userName);
            req.setAttribute("userQuestion", user != null ? user.getUserQuestion() : "");
            req.getRequestDispatcher("/resetQuestion.jsp").forward(req, resp);
            return;
        }

        // 重置密码
        user.setUserPassword(newPassword);
        boolean success = userService.updateUser(user);
        if (success) {
            req.setAttribute("successMsg", "密码重置成功，请使用新密码登录");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMsg", "密码重置失败，请稍后再试");
            req.setAttribute("userName", userName);
            req.setAttribute("userQuestion", user.getUserQuestion());
            req.getRequestDispatcher("/resetQuestion.jsp").forward(req, resp);
        }
    }

    /**
     * 重置密码
     */
    private void resetPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        if (userName == null || userName.isEmpty()) {
            req.setAttribute("errorMsg", "用户名不能为空");
            req.getRequestDispatcher("/resetPassword.jsp").forward(req, resp);
            return;
        }

        // 重定向到安全问题验证页面
        resp.sendRedirect(req.getContextPath() + "/user/reset-question?userName=" + userName);
    }
} 