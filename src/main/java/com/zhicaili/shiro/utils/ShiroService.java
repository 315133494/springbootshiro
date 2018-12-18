package com.zhicaili.shiro.utils;

import com.zhicaili.shiro.pojo.Resources;
import com.zhicaili.shiro.service.ResourcesService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.velocity.runtime.directive.MacroParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiroService {
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
    /**
     * 初始化权限
     * @return
     */
    public Map<String, String> loadFilterChainDefinition() {


        //拦截器
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        //配置退出 过滤器，其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/font-awesome/**", "anon");
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
        List<Resources> resourcesList = resourcesService.queryAll();
        for (Resources resources : resourcesList) {
            if (StringUtils.isNotEmpty(resources.getResUrl())) {
                String permission = "perms[" + resources.getResUrl() + "]";
                filterChainDefinitionMap.put(resources.getResUrl(), permission);
            }
        }
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    /**
     * 重新加载权限
     */
    public void updatePermission(){
        synchronized (shiroFilterFactoryBean){
            AbstractShiroFilter shiroFilter=null;
            try {
                shiroFilter=(AbstractShiroFilter)shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver=(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager=(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
            //清空老的权限控制
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinition());

            //重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String,String> entry:chains.entrySet()){
                String url=entry.getValue();
                String chainDefinition = entry.getValue().trim().replace(" ","");
                manager.createChain(url,chainDefinition);
            }
            System.out.println("更新权限成功!");
        }
    }
}
