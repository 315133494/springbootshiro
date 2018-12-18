package com.zhicaili.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhicaili.shiro.mapper.RoleResourcesMapper;
import com.zhicaili.shiro.pojo.Role;
import com.zhicaili.shiro.mapper.RoleMapper;
import com.zhicaili.shiro.pojo.RoleResources;
import com.zhicaili.shiro.pojo.RoleVo;
import com.zhicaili.shiro.pojo.User;
import com.zhicaili.shiro.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourcesMapper roleResourcesMapper;
    @Override
    public List<RoleVo> queryRoleListWithSelected(Integer uid) {
        return roleMapper.queryRoleListWithSelected(uid);

    }

    /**
     * 分页查询用户列表
     * @param role
     * @param start
     * @param length
     * @return
     */
    @Override
    public IPage<Role> selectByPage(Role role, int start, int length) {
        int current = start / length + 1;//当前页
        Wrapper<User> wrapper = new QueryWrapper<>();

        IPage<Role> page = new Page<>(current, length);

        IPage<Role> userIPage = roleMapper.selectPage(page, null);
        return userIPage;
    }

    /**
     * 根据id删除角色
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        //1、先删除角色关联的资源
        Wrapper<RoleResources> wrapper=new QueryWrapper<>();
        ((QueryWrapper<RoleResources>) wrapper).eq("roleId",id);

        try {
        roleResourcesMapper.delete(wrapper);
            //2、再删除角色
            roleMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据角色名称查询角色
     * @param roleDesc
     * @return
     */
    @Override
    public Role selectRoleByRoleDesc(String roleDesc) {
        Wrapper<Role> wrapper=new QueryWrapper<>();
        ((QueryWrapper<Role>) wrapper).eq("roleDesc",roleDesc);
        Role role = roleMapper.selectOne(wrapper);
        return role;
    }
}
