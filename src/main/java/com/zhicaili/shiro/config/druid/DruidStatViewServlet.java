package com.zhicaili.shiro.config.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * 配置druid页面配置
 * 定义Servlet，用于Druid控制台的查看
 */
@WebServlet(urlPatterns="/druid/*",initParams = {
        @WebInitParam(name="allow",value = "127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
        @WebInitParam(name = "loginUsername",value = "root"),//用户名
        @WebInitParam(name = "loginPassword",value = "admin"),//密码
        @WebInitParam(name = "resetEnable",value = "true")// 允许HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet implements Servlet {
}
