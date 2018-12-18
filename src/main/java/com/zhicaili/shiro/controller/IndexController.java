package com.zhicaili.shiro.controller;

import com.zhicaili.shiro.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, User user  , Model model){
        //判断用户名或者密码是否为空
        if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            request.setAttribute("msg","用户名或密码不能为空");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            return "redirect:userPage";
        } catch (UnknownAccountException e) {
            token.clear();
            request.setAttribute("msg", "用户名或密码不正确！");
            return "login";
        }catch (LockedAccountException  e){
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        }
    }


    /**
     * 跳转到用户管理页面
     * @return
     */
    @RequestMapping(value = {"/userPage",""})
    public String userPage(){
        return "user/users";
    }

    /**
     * 跳转到角色管理页面
     * @return
     */
    @RequestMapping("/rolePage")
    public String rolePage(){
        return "role/roles";
    }

    /**
     * 跳转到资源管理页面
     * @return
     */
    @RequestMapping(value = "/resourcesPage")
    public String resourcesPage(){
        return "resources/resources";
    }

    /**
     * 无权限 时跳转到错误页面
     * @return
     */
    @RequestMapping(value = "/403")
    public  String forbidden(){
        return "403";
    }
    /**
     * 页面找不到，跳转到错误页面
     * @return
     */
    @RequestMapping(value = "/404")
    public  String errror(){
        return "404";
    }
}
