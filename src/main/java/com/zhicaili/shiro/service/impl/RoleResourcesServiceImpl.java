package com.zhicaili.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhicaili.shiro.mapper.UserRoleMapper;
import com.zhicaili.shiro.pojo.RoleResources;
import com.zhicaili.shiro.mapper.RoleResourcesMapper;
import com.zhicaili.shiro.pojo.RoleResourcesVo;
import com.zhicaili.shiro.realms.ShiroRealm;
import com.zhicaili.shiro.service.RoleResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoleResourcesServiceImpl extends ServiceImpl<RoleResourcesMapper, RoleResources> implements RoleResourcesService {
    @Autowired
    private RoleResourcesMapper roleResourcesMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private ShiroRealm shiroRealm;
    /**
     * 添加角色拥有的资源
     * @param roleResourcesVo
     */
    @Override
    public void addRoleResources(RoleResourcesVo roleResourcesVo) {
        //1、先删除角色拥有的资源，再添加
        Wrapper<RoleResources> wrapper=new QueryWrapper<>();
        ((QueryWrapper<RoleResources>) wrapper).eq("roleId",roleResourcesVo.getRoleId());
        try {
            roleResourcesMapper.delete(wrapper);

            //2、添加
            if (StringUtils.isNotEmpty(roleResourcesVo.getResourcesId())){
                String[] resourcesIds = roleResourcesVo.getResourcesId().split(",");
                for (String resourcesId : resourcesIds) {
                    RoleResources roleResources=new RoleResources();
                    roleResources.setRoleId(roleResourcesVo.getRoleId());
                    roleResources.setResourcesId(Integer.parseInt(resourcesId));
                    //插入
                    roleResourcesMapper.insert(roleResources);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Integer> userIds= userRoleMapper.findUserIdByRoleId(roleResourcesVo.getRoleId());
        //更新当前登录的用户的权限缓存
        shiroRealm.clearUserAuthByUserId(userIds);
    }
}
