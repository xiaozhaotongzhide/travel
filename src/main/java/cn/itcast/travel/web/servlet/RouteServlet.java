package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceimpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RouteServlet", value = "/route/*")
public class RouteServlet extends BaseServlet {
    RouteService routesercice = new RouteServiceImpl();
    FavoriteService favoriteService = new FavoriteServiceimpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接受搜索参数rname
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        int cid = 0;
        //处理参数
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int pageSize = 0;
        //每页显示条数不传默认五条
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        int currentPage = 0;
        //处理当前页数，不传，默认第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        //3.调用service查询PageBaen对象
        PageBean<Route> pb = routesercice.pageQuery(cid, currentPage, pageSize, rname);
        //4.写回客户端
        writeValue(pb, response);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service
        Route route = routesercice.findOne(rid);
        //3.转化为json
        writeValue(route, response);
    }

    /**
     * 判断当前用户是否收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.获取uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        //调用FavoriteService是否收藏
        boolean favorite = favoriteService.isFavorite(rid, uid);
        writeValue(favorite, response);
    }

    /**
     * 添加收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.获取uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        //3.调用add添加收藏
        favoriteService.addFavorite(rid, uid);
    }

    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }
        //接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = 0;
        //每页显示条数不传默认五条
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 12;
        }
        int currentPage = 0;
        //处理当前页数，不传，默认第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        //调用favoriteService
        PageBean<Route> myfavorite = favoriteService.myfavorite(uid, pageSize, currentPage);
        //4.写回客户端
        writeValue(myfavorite, response);
    }
}