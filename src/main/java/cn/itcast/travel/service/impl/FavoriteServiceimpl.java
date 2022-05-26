package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoimlp;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceimpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoimlp();
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite byRidAndUid = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return byRidAndUid != null;//如果对象有值，则为ture，反之则为false
    }

    @Override
    public void addFavorite(String rid, int uid) {
        favoriteDao.addFavorite(Integer.parseInt(rid),uid);
    }

    @Override
    public PageBean<Route> myfavorite(int uid, int pageSize, int currentPage) {
        //封装pageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示的条数
        pb.setPageSive(pageSize);
        //设置总记录数
        int totalCount = favoriteDao.findCount(uid);
        pb.setTotalCount(totalCount);
        //设置当前页面的数据集合
        int start = (currentPage - 1)*pageSize;//开始的记录数
        List<Route> list = favoriteDao.findByPageUid(start,pageSize,uid);
        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize)+1;
        pb.setTotalPage(totalPage);
        return pb;
    }

}
