package com.zhicaili.shiro.service;

import com.zhicaili.shiro.pojo.RoleResources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhicaili.shiro.pojo.RoleResourcesVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
public interface RoleResourcesService extends IService<RoleResources> {
    /**
     * 添加角色拥有的资源
     * @param roleResourcesVo
     */
    void addRoleResources(RoleResourcesVo roleResourcesVo);
}
