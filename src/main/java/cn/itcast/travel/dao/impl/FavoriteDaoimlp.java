package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import com.alibaba.druid.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoimlp implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据uid和rid查询
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ? ";
        //防止没查到报错
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";

        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }

    @Override
    public int findCount(int uid) {
        //1.定义sql模板
        String sql = "select count(*) from tab_favorite where uid = ? ";
        return template.queryForObject(sql,Integer.class,uid);
    }

    @Override
    public List<Route> findByPageUid(int start, int pageSize, int uid) {
        String sql = " select * from tab_route,tab_favorite where tab_route.rid = tab_favorite.rid and tab_favorite.uid = ?  limit ? , ? ";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),uid,start,pageSize);
    }


}
