package com.zhicaili.shiro.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhicaili.shiro.pojo.Resources;
import com.zhicaili.shiro.pojo.ResourcesVo;
import com.zhicaili.shiro.pojo.Role;
import com.zhicaili.shiro.realms.ShiroRealm;
import com.zhicaili.shiro.service.ResourcesService;
import com.zhicaili.shiro.utils.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhicaili
 * @since 2018-12-03
 */
@RestController
@RequestMapping("/resources")
public class ResourcesController {
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private ShiroService shiroService;
    @RequestMapping
    public Map<String,Object> getAll(Resources resources, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){
        Map<String,Object> map = new HashMap<>();
        IPage<Resources> resourcesIPage=resourcesService.selectByPage(resources, start, length);
        map.put("draw",draw);
        map.put("recordsTotal",resourcesIPage.getTotal());
        map.put("recordsFiltered",resourcesIPage.getTotal());
        map.put("data", resourcesIPage.getRecords());
        return map;
    }


    /**
     * 查询用户选择的资源
     * @return
     */
    @GetMapping("/resourcesWithSelected")
    @ResponseBody
    public List<ResourcesVo> resourcesWithSelected(Integer rid){
        List<ResourcesVo> resourcesVoList= resourcesService.queryResourcesListWithSelected(rid);
        return resourcesVoList;
    }



    @RequestMapping("/select")
    public List<Resources> select(){
        Map<String, Object> map=new HashMap<>();
        map.put("userid",1);
        map.put("type",null);
        List<Resources> resources = resourcesService.loadUserResources(1,null);
        for (Resources resource : resources) {
            System.out.println(resource);
        }
        return resources;
    }

    /**
     * 加载菜单
     * @return
     */
    @RequestMapping("loadMenu")
    public List<Resources> loadMenu(){
        Integer userId = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
        Integer type=1;
        List<Resources> resources = resourcesService.loadUserResources(userId, type);
        for (Resources resource : resources) {
            System.out.println(resource);
        }
        return resources;

    }

    /**
     * 添加资源
     * @param resources
     * @return
     */
    @PostMapping("/add")
    public String add(Resources resources){
        try{
            resourcesService.save(resources);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 根据id删除资源
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete")
    public String delete(Integer id){
        try{
            resourcesService.removeById(id);
            //更新权限
            shiroService.updatePermission();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

}

