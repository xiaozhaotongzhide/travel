package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    public Favorite findByRidAndUid(int rid,int uid);

    int findCountByRid(int rid);

    void addFavorite(int rid, int uid);

    int findCount(int uid);

    List<Route> findByPageUid(int start, int pageSize, int uid);
}
