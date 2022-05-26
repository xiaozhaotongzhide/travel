package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface FavoriteService {

    public boolean isFavorite(String rid,int uid);

    public void addFavorite(String rid, int uid);

    PageBean<Route> myfavorite(int uid, int pageSize, int currentPage);
}
