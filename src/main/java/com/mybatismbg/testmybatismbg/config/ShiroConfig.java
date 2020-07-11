package com.mybatismbg.testmybatismbg.config;

import com.mybatismbg.testmybatismbg.realms.MyRealm;
import com.mybatismbg.testmybatismbg.realms.SecondRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 创建Realm
    @Bean
    public AuthorizingRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        // 设置凭证匹配器为HashedCredentialsMatcher
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5"); //使用MD5加密的方法
        hashedCredentialsMatcher.setHashIterations(5); // 进行md5加密5次
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    // 创建第二个realm
    @Bean
    public AuthorizingRealm secondRealm(){
        SecondRealm secondRealm = new SecondRealm();
        // 设置凭证管理器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-1"); // 使用SHA-1加密
        hashedCredentialsMatcher.setHashIterations(5); // 加密5次
        secondRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return secondRealm;
    }

    // 创建Authenticator
    @Primary //使他成为默认的
    @Bean
    public Authenticator authenticator(){
        // 创建实例
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        // 设置认证策略,所有的realm都必须成功
        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        return authenticator;
    }

    // 创建SecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager(Authenticator authenticator,List<Realm> realms){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 设置Authenticator
        defaultWebSecurityManager.setAuthenticator(authenticator);
        // 设置多realm,授权时需要这样设置
        defaultWebSecurityManager.setRealms(realms);
        // 设置cookie保存时间
        // 创建rememberMe管理器
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        // 创建简单的缓存
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        // 设置保存缓存为7天
        simpleCookie.setMaxAge(7*24*60*60);
        // 设置cookie
        cookieRememberMeManager.setCookie(simpleCookie);
        // 设置rememberMe管理器
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        return defaultWebSecurityManager;
    }

    // 创建对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String,String> map = new LinkedHashMap<>();
        /*
        * 权限的解释:
        *   anon 可以匿名访问
        *   authc 必须登录认证后才能访问
        *   logout 退出登录
        *   user 要么通过登录,要么通过记住我(RememberMe cookie)
        * url匹配的模式(支持ant风格):
        *   ?:匹配一个字符
        *   *:匹配零个或者多个字符串
        *   **:匹配路径中零个或者多个路径,/**表示匹配/下的任意层的任意路径
        * 匹配机制采用第一次匹配优先:(从上倒下优先匹配)
        * */
        // 开放的
        map.put("/","anon");
        map.put("/login","anon");
        map.put("/shirologin","anon");
        // 退出登录
        map.put("/shiroLogout","logout");
        /*
        * 角色过滤器:roles ==> 只有具备对应的角色才能访问对应的资源
        * */
        map.put("/userpage","roles[user]");
        map.put("/empage","roles[admin]");
        //判断是否是通过认证或者是通过记住我
        map.put("/**","anon");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/success");
        //没有权限的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/nothave");
        // 将map设置进factory
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
