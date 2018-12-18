package com.zhicaili.shiro.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置druid需要的配置类，引入application.yml文件中以spring.datasource开关的信息，
 * 因此需要在application.yml文件中配置相关信息
 */

@Configuration//标识该类被纳入spring窗口中实例化长管理
@ServletComponentScan//用于扫描所有的servlet,filter,listener
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")//加载时读取指定的配置信息，前缀为spring.datasource.druid
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
}
