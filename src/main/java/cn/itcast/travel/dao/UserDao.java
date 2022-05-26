package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     */
    public User findByUsername(String username);
    public void save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByNameAndPassword(String name,String password);
}
