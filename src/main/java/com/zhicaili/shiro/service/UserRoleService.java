package com.zhicaili.shiro.service;

import com.zhicaili.shiro.pojo.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhicaili.shiro.pojo.UserRoleVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 保存用户角色
     * @param userRoleVo
     */
    void addUserRole(UserRoleVo userRoleVo);
}
