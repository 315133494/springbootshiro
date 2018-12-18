package com.zhicaili.shiro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhicaili.shiro.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhicaili.shiro.pojo.RoleVo;
import com.zhicaili.shiro.pojo.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询可以分配的角色
     * @param uid
     * @return
     */
    List<RoleVo> queryRoleListWithSelected(Integer uid);

    /**
     * 分页查询用户列表
     * @param role
     * @param start
     * @param length
     * @return
     */
    IPage<Role> selectByPage(Role role, int start, int length);

    /**
     * 根据id删除角色
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据角色名称查询
     * @param roleDesc
     * @return
     */
    Role selectRoleByRoleDesc(String roleDesc);
}
