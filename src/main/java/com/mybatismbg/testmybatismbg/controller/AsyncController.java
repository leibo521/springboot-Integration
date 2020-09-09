package com.mybatismbg.testmybatismbg.controller;

import com.mybatismbg.testmybatismbg.bean.Employees;
import com.mybatismbg.testmybatismbg.service.AsyncService;
import com.mybatismbg.testmybatismbg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/async")
@RestController
public class AsyncController {

    // 注入service
    @Autowired
    EmpService empService;
    @Autowired
    AsyncService asyncService;

    // 测试一个异步的注解
    @RequestMapping("/hello")
    public Employees asyncHello() {
        log.info("将会开始执行异步的方法");
        asyncService.hello();
        return empService.getEmpById(101);
    }


}
