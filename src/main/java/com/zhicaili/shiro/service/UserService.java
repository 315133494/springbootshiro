package com.zhicaili.shiro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhicaili.shiro.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User selectByUsername(String username);

    /**
     * 分页查询用户列表
     * @param user
     * @param start
     * @param length
     */
    IPage<User> selectByPage(User user, int start, int length);


    /**
     * delete删除用户
     * @param id
     */
    void deleteById(Integer id);
}
