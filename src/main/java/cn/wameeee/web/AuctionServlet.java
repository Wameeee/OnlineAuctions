package cn.wameeee.web;

import cn.wameeee.entity.Auction;
import cn.wameeee.entity.AuctionRecord;
import cn.wameeee.entity.AuctionUser;
import cn.wameeee.service.AuctionRecordService;
import cn.wameeee.service.AuctionService;
import cn.wameeee.service.impl.AuctionRecordServiceImpl;
import cn.wameeee.service.impl.AuctionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 拍卖品相关的Servlet控制器
 */
@WebServlet(urlPatterns = {"/auction/*"})
@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // 10MB
public class AuctionServlet extends HttpServlet {

    private AuctionService auctionService = new AuctionServiceImpl();
    private AuctionRecordService recordService = new AuctionRecordServiceImpl();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            path = "/list";
        }

        switch (path) {
            case "/list":
                listAuctions(req, resp);
                break;
            case "/ongoing":
                listOngoingAuctions(req, resp);
                break;
            case "/finished":
                listFinishedAuctions(req, resp);
                break;
            case "/detail":
                showAuctionDetail(req, resp);
                break;
            case "/add":
                showAddAuctionForm(req, resp);
                break;
            case "/search":
                searchAuctions(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/auction/list");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            path = "/list";
        }

        switch (path) {
            case "/add":
                addAuction(req, resp);
                break;
            case "/update":
                updateAuction(req, resp);
                break;
            case "/delete":
                deleteAuction(req, resp);
                break;
            case "/bid":
                bidAuction(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/auction/list");
                break;
        }
    }

    /**
     * 显示所有拍卖品列表
     */
    private void listAuctions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Auction> auctions = auctionService.getAllAuctions();
        req.setAttribute("auctions", auctions);
        req.getRequestDispatcher("/auctionList.jsp").forward(req, resp);
    }

    /**
     * 显示正在进行中的拍卖品列表
     */
    private void listOngoingAuctions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Auction> auctions = auctionService.getOngoingAuctions();
        req.setAttribute("auctions", auctions);
        req.setAttribute("auctionStatus", "ongoing");
        req.getRequestDispatcher("/auctionList.jsp").forward(req, resp);
    }

    /**
     * 显示已结束的拍卖品列表
     */
    private void listFinishedAuctions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Auction> auctions = auctionService.getFinishedAuctions();
        req.setAttribute("auctions", auctions);
        req.setAttribute("auctionStatus", "finished");
        req.getRequestDispatcher("/auctionList.jsp").forward(req, resp);
    }

    /**
     * 显示拍卖品详情
     */
    private void showAuctionDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String auctionIdStr = req.getParameter("id");
        if (auctionIdStr == null || auctionIdStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/auction/list");
            return;
        }

        try {
            Long auctionId = Long.parseLong(auctionIdStr);
            Auction auction = auctionService.getAuctionById(auctionId);
            if (auction == null) {
                resp.sendRedirect(req.getContextPath() + "/auction/list");
                return;
            }

            // 获取竞拍记录
            List<AuctionRecord> records = recordService.getRecordsSortedByPrice(auctionId);
            req.setAttribute("auction", auction);
            req.setAttribute("records", records);

            // 判断拍卖是否已结束
            Date now = new Date();
            boolean isFinished = now.after(auction.getAuctionEndTime());
            req.setAttribute("isFinished", isFinished);

            // 获取最高竞拍记录
            AuctionRecord highestRecord = recordService.getHighestRecord(auctionId);
            req.setAttribute("highestRecord", highestRecord);

            req.getRequestDispatcher("/auctionDetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/auction/list");
        }
    }

    /**
     * 显示添加拍卖品表单
     */
    private void showAddAuctionForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否是管理员
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null || !user.getUserIsAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        req.getRequestDispatcher("/addAuction.jsp").forward(req, resp);
    }

    /**
     * 添加拍卖品
     */
    private void addAuction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否是管理员
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null || !user.getUserIsAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // 获取表单数据
            String auctionName = req.getParameter("auctionName");
            BigDecimal startPrice = new BigDecimal(req.getParameter("auctionStartPrice"));
            BigDecimal upsetPrice = new BigDecimal(req.getParameter("auctionUpset"));
            Date startTime = dateFormat.parse(req.getParameter("auctionStartTime"));
            Date endTime = dateFormat.parse(req.getParameter("auctionEndTime"));
            String auctionDesc = req.getParameter("auctionDesc");

            // 处理图片上传
            Part filePart = req.getPart("auctionPic");
            String fileName = getFileName(filePart);
            String fileExt = getFileExtension(fileName);

            // 创建拍卖品对象
            Auction auction = new Auction();
            auction.setAuctionName(auctionName);
            auction.setAuctionStartPrice(startPrice);
            auction.setAuctionUpset(upsetPrice);
            auction.setAuctionStartTime(startTime);
            auction.setAuctionEndTime(endTime);
            auction.setAuctionDesc(auctionDesc);
            auction.setAuctionPic(fileName);
            auction.setAuctionPicType(fileExt);

            // 保存拍卖品
            boolean success = auctionService.addAuction(auction);
            if (success) {
                // 保存图片文件
                String uploadPath = getServletContext().getRealPath("/upload");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                filePart.write(uploadPath + File.separator + fileName);
                resp.sendRedirect(req.getContextPath() + "/auction/list");
            } else {
                req.setAttribute("errorMsg", "添加拍卖品失败");
                req.getRequestDispatcher("/addAuction.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "添加拍卖品失败：" + e.getMessage());
            req.getRequestDispatcher("/addAuction.jsp").forward(req, resp);
        }
    }

    /**
     * 更新拍卖品
     */
    private void updateAuction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否是管理员
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null || !user.getUserIsAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // 获取表单数据
            Long auctionId = Long.parseLong(req.getParameter("auctionId"));
            String auctionName = req.getParameter("auctionName");
            BigDecimal startPrice = new BigDecimal(req.getParameter("auctionStartPrice"));
            BigDecimal upsetPrice = new BigDecimal(req.getParameter("auctionUpset"));
            Date startTime = dateFormat.parse(req.getParameter("auctionStartTime"));
            Date endTime = dateFormat.parse(req.getParameter("auctionEndTime"));
            String auctionDesc = req.getParameter("auctionDesc");

            // 获取原拍卖品信息
            Auction auction = auctionService.getAuctionById(auctionId);
            if (auction == null) {
                resp.sendRedirect(req.getContextPath() + "/auction/list");
                return;
            }

            // 更新拍卖品信息
            auction.setAuctionName(auctionName);
            auction.setAuctionStartPrice(startPrice);
            auction.setAuctionUpset(upsetPrice);
            auction.setAuctionStartTime(startTime);
            auction.setAuctionEndTime(endTime);
            auction.setAuctionDesc(auctionDesc);

            // 处理图片上传
            Part filePart = req.getPart("auctionPic");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = getFileName(filePart);
                String fileExt = getFileExtension(fileName);
                auction.setAuctionPic(fileName);
                auction.setAuctionPicType(fileExt);

                // 保存图片文件
                String uploadPath = getServletContext().getRealPath("/upload");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                filePart.write(uploadPath + File.separator + fileName);
            }

            // 保存拍卖品
            boolean success = auctionService.updateAuction(auction);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/auction/detail?id=" + auctionId);
            } else {
                req.setAttribute("errorMsg", "更新拍卖品失败");
                req.setAttribute("auction", auction);
                req.getRequestDispatcher("/addAuction.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "更新拍卖品失败：" + e.getMessage());
            req.getRequestDispatcher("/addAuction.jsp").forward(req, resp);
        }
    }

    /**
     * 删除拍卖品
     */
    private void deleteAuction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否是管理员
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null || !user.getUserIsAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Long auctionId = Long.parseLong(req.getParameter("id"));
            boolean success = auctionService.deleteAuction(auctionId);
            resp.sendRedirect(req.getContextPath() + "/auction/list");
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/auction/list");
        }
    }

    /**
     * 竞拍
     */
    private void bidAuction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否登录
        AuctionUser user = (AuctionUser) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Long auctionId = Long.parseLong(req.getParameter("auctionId"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            
            boolean success = recordService.bid(auctionId, user.getUserId(), price);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/auction/detail?id=" + auctionId);
            } else {
                req.setAttribute("errorMsg", "竞拍失败，请确保价格高于当前最高价");
                showAuctionDetail(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "竞拍失败：" + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/auction/list");
        }
    }

    /**
     * 搜索拍卖品
     */
    private void searchAuctions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword != null && !keyword.isEmpty()) {
            List<Auction> auctions = auctionService.getAuctionsByName(keyword);
            req.setAttribute("auctions", auctions);
            req.setAttribute("keyword", keyword);
        } else {
            List<Auction> auctions = auctionService.getAllAuctions();
            req.setAttribute("auctions", auctions);
        }
        req.getRequestDispatcher("/auctionList.jsp").forward(req, resp);
    }

    /**
     * 获取上传文件的文件名
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
} 