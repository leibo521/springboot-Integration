package com.mybatismbg.testmybatismbg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync //开启异步注解
@EnableCaching //开启基于注解的缓存
@EnableScheduling //开始定时任务
@MapperScan(value = "com.mybatismbg.testmybatismbg.mappers") //指定mapper所在的包
@SpringBootApplication
public class TestmybatismbgApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestmybatismbgApplication.class, args);
    }

}
