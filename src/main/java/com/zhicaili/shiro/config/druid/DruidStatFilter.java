package com.zhicaili.shiro.config.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 定义Filter，忽略静态资源的拦截
 */
@WebFilter(filterName = "druidWebStatFilter",
        urlPatterns = "/",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.jpg,*.png,*.gif,*.ico,*.css,/druid/*")//配置本过滤器所放行的请求后缀
        }
)
public class DruidStatFilter extends WebStatFilter {

}
