package com.mybatismbg.testmybatismbg.controller;

import com.mybatismbg.testmybatismbg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Slf4j
@Controller //该控制器控制视图的跳转
public class ViewController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/success")
    public String loginSuccess() {
        return "success";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/nothave")
    public String notHave() {
        return "NotHave";
    }

    @RequestMapping("/userpage")
    public String getUser() {
        return "user";
    }

    @RequestMapping("/empage")
    public String getEmp() {
        return "employee";
    }


    // 登录的请求
    @PostMapping("/shirologin")
    public String shiroLogin(@RequestParam("username") String name, @RequestParam("password") String password) {
        log.info("登录的请求已经接收");
        // 获取当前的subject
        Subject subjectUser = SecurityUtils.getSubject();
        // 判断当前的subject是否被认证
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        // 设置记住我
        token.setRememberMe(true);
        try {
            subjectUser.login(token);
            log.info("登录成功,正在前往页面");
        } catch (AuthenticationException ae) {
            log.warn("用户登录失败," + ae.getMessage());
            return "redirect:/login";
        }
        return "redirect:/success";
    }

    @Autowired
    EmpService empService;

    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    //表示这个方法需要当前用户具有admin这个角色,否则抛出org.apache.shiro.authz.AuthorizationException
    @RequestMapping("seeadmin")
    public String seeadmin(Map<String, Object> map) {
        map.put("emp", empService.getEmpById(101));
        log.info(empService.getEmpById(101).toString());
        return "employee";
    }

    // 使用这个注解来捕获这个异常,在发生这个异常时重定向到首页
    @ExceptionHandler(value = {AuthorizationException.class})
    public String getException() {
        return "redirect:/";
    }

}
