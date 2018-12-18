package com.zhicaili.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhicaili.shiro.pojo.UserRole;
import com.zhicaili.shiro.mapper.UserRoleMapper;
import com.zhicaili.shiro.pojo.UserRoleVo;
import com.zhicaili.shiro.realms.ShiroRealm;
import com.zhicaili.shiro.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private ShiroRealm shiroRealm;
    /**
     * 保存用户角色
     * @param userRoleVo
     */
    @Override
    public void addUserRole(UserRoleVo userRoleVo) {
        //先删除
        Wrapper<UserRole> wrapper=new QueryWrapper<>();
        ((QueryWrapper<UserRole>) wrapper).eq("userId",userRoleVo.getUserId());
        userRoleMapper.delete(wrapper);

        //再添加
        String[] roleIds = userRoleVo.getRoleId().split(",");
        for (String roleId : roleIds) {
            UserRole userRole=new UserRole();
            userRole.setUserId(userRoleVo.getUserId());
            userRole.setRoleId(Integer.parseInt(roleId));
            userRoleMapper.insert(userRole);
        }

        //更新当前登录的用户的权限缓存
        List<Integer> userId = new ArrayList<>();
        userId.add(userRoleVo.getUserId());
        shiroRealm.clearUserAuthByUserId(userId);
    }
}
