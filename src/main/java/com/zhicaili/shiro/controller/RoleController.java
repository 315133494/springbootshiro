package com.zhicaili.shiro.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhicaili.shiro.pojo.*;
import com.zhicaili.shiro.service.RoleResourcesService;
import com.zhicaili.shiro.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleResourcesService roleResourcesService;

    @RequestMapping
    public Map<String, Object> getAll(Role role, String draw,
                                      @RequestParam(required = false, defaultValue = "1") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length) {
        //分页查询
        IPage<Role> roleIPage = roleService.selectByPage(role, start, length);
        System.out.println("total-=================" + roleIPage.getTotal());
        Map<String, Object> map = new HashMap<>();
        map.put("draw", draw);
        map.put("recordsTotal", roleIPage.getTotal());
        map.put("recordsFiltered", roleIPage.getTotal());
        map.put("data", roleIPage.getRecords());

        return map;
    }


    @GetMapping("/roleWithSelected")
    public List<RoleVo> roleWithSelected(Integer uid) {
        List<RoleVo> roles = roleService.queryRoleListWithSelected(uid);
        for (RoleVo role : roles) {
            System.out.println(role);
        }
        return roles;

    }

    @PostMapping("/saveRoleResources")
    public String saveRoleResources(RoleResourcesVo roleResourcesVo) {
        //角色 id空
        if (StringUtils.isEmpty(roleResourcesVo.getRoleId().toString())) {
            return "error";
        }
        try {
            //添加角色拥有的资源
            roleResourcesService.addRoleResources(roleResourcesVo);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public String delete(Integer id) {
        try {
            roleService.deleteById(id);
            return "success";
        } catch (Exception e ) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping("/add")
    public String add(Role role){
        //先查询角色是否存在
       Role r= roleService.selectRoleByRoleDesc(role.getRoleDesc());
        if (r!=null){//角色存在，直接返回错误
            return "error";
        }
        try {
            //保存角色
            roleService.save(role);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}

