package com.mybatismbg.testmybatismbg.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;


@Configuration
public class CustomConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    // 该类的bean会导致输入失败
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
}
