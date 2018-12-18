package com.zhicaili.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhicaili.shiro.mapper.UserRoleMapper;
import com.zhicaili.shiro.pojo.User;
import com.zhicaili.shiro.mapper.UserMapper;
import com.zhicaili.shiro.pojo.UserRole;
import com.zhicaili.shiro.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根s据用户名查询用户
     *
     * @param username
     * @return
     */
    public User selectByUsername(String username) {
        Wrapper<User> wrapper = new QueryWrapper<>();
        //根据用户名松紧度
        ((QueryWrapper<User>) wrapper).eq("username", username);
        //执行查询
        User user = userMapper.selectOne(wrapper);
        //返回user对象
        return user;
    }

    @Override
    public IPage<User> selectByPage(User user, int start, int length) {
        int current = start / length + 1;//当前页
        Wrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(user.getUsername())) {
            ((QueryWrapper<User>) wrapper).like("username", user.getUsername());
        }
        if (user.getId() != null) {
            ((QueryWrapper<User>) wrapper).eq("id", user.getId());
        }
        if (user.getEnable() != null) {
            ((QueryWrapper<User>) wrapper).eq("enable", user.getEnable());
        }

        IPage<User> page = new Page<>(current, length);

        IPage<User> userIPage = userMapper.selectPage(page, wrapper);
        return userIPage;
    }

    /**
     * delete删除用户
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //1、先删除用户关联的角色
        Wrapper<UserRole> wrapper = new QueryWrapper<>();
        ((QueryWrapper<UserRole>) wrapper).eq("userId", id);
        try {
            userRoleMapper.delete(wrapper);
            //2、再删除用户
            userMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
