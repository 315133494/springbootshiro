package com.zhicaili.shiro.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhicaili.shiro.pojo.User;
import com.zhicaili.shiro.pojo.UserRole;
import com.zhicaili.shiro.pojo.UserRoleVo;
import com.zhicaili.shiro.service.UserRoleService;
import com.zhicaili.shiro.service.UserService;
import com.zhicaili.shiro.utils.PasswordHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.pl.REGON;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping
    public Map<String, Object> getAll(User user, String draw,
                                      @RequestParam(required = false, defaultValue = "1") int start,//开始记录
                                      @RequestParam(required = false, defaultValue = "10") int length//每页显示的记录数
    ) {
        //分页查询
        IPage<User> userIPage = userService.selectByPage(user, start, length);
        System.out.println("total-=================" + userIPage.getTotal());
        Map<String, Object> map = new HashMap<>();
        map.put("draw", draw);
        map.put("recordsTotal", userIPage.getTotal());
        map.put("recordsFiltered", userIPage.getTotal());
        map.put("data", userIPage.getRecords());
        return map;
    }

    /**
     * 保存用户角色
     *
     * @param userRoleVo
     * @return
     */
    @PostMapping("/saveUserRoles")
    public String saveUserRoles(UserRoleVo userRoleVo) {
        if (StringUtils.isEmpty(userRoleVo.getUserId().toString())) {
            return "error";
        }
        try {
            userRoleService.addUserRole(userRoleVo);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }


    /**
     * delete删除用户
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public String deleteById(Integer id) {
        try {
            userService.deleteById(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @PostMapping("/add")
    public String add(User user){
        User u = userService.selectByUsername(user.getUsername());
        if (u!=null){
            return "error";
        }
        try {
            //设置用户为启用
            user.setEnable(1);
            //密码加盐
            PasswordHelper passwordHelper=new PasswordHelper();
            passwordHelper.encryptPassword(user);
            //添加用户
            userService.save(user);
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}

