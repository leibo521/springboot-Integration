package com.mybatismbg.testmybatismbg.realms;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Slf4j
public class MyRealm extends AuthorizingRealm {
    // 授权时会被调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 进行授权的方法
        // ① 从参数获取用户的信息
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        // ② 使用信息获取用户的角色信息,可能需要查询数据库
        Set<String> roles = new HashSet<>();
        roles.add("user");
        if ("admin".equals(primaryPrincipal)){
            roles.add("admin");
        }
        // ③ 创建SimpleAuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /* 输出token */
        log.info("正在运行的是MyRealm");
        /* 实现这个类 */
        // ① 将AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        // ② 从UsernamePasswordToken 中获取username
        String name = usernamePasswordToken.getUsername();
        // ③ 调用数据库的方法,从数据库中查询Username对应的记录
        log.info("将从数据库中查询 [" + name + "] 的用户信息");
        // ④ 若用户不存在,则抛出UnKownAccountException 异常
        if ("unkown".equals(name)) {
            throw new UnknownAccountException("没有找到 [" + name + "] 的用户信息!");
        }
        // ⑤ 根据用户的情况,决定是否需要抛出其他的异常
        if ("lock".equals(name)) {
            // 抛出用户被锁定的异常
            throw new LockedAccountException("[" + name + "] 的用户信息已经被锁定");
        }
        // ⑥ 构建AuthenticationInfo对象并返回
        // principal : 认证的实体信息,可以是username,也可以是数据表对应的用户的实体类对象
        // 产生随机的盐值
        int i = new Random().nextInt(10000);
        ByteSource salt = ByteSource.Util.bytes("user"+i);
        SimpleHash md5 = new SimpleHash("md5", "654321", salt, 5);
        // credentials : 是从数据库中获取的密码
        Object credentials = md5.toString();
        /*
         * md5加密使用:new SimpleHash(hashAlgorithmName("MD5"), credentials(密码的char数组), salt(null), hashIterations(5)加密次数);
         * */
        // 盐值:bytesource类型,传入一个唯一的字符串
        char[] password = usernamePasswordToken.getPassword();
        String s = String.copyValueOf(password);
        log.info("客户端传过来的密码是:" + s);
        log.info("密码输入正确,将会构建用户的token信息,即将登录成功.");
        // realmName : 当前realm对象的name,调用父类的getName()即可
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, credentials, salt, getName());
//        simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        log.info("运行MyRealm结束");
        return simpleAuthenticationInfo;
    }
}
