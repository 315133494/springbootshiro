package com.zhicaili.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.swing.*;

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.zhicaili.shiro.mapper")
public class SpringbootShiroApplication  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(SpringbootShiroApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroApplication.class, args);
    }
}
