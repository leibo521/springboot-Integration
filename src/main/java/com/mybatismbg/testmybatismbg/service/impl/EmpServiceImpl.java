package com.mybatismbg.testmybatismbg.service.impl;

import com.mybatismbg.testmybatismbg.bean.Employees;
import com.mybatismbg.testmybatismbg.bean.EmployeesExample;
import com.mybatismbg.testmybatismbg.mappers.EmployeesMapper;
import com.mybatismbg.testmybatismbg.service.EmpService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@CacheConfig(cacheNames = "empcache")
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    EmployeesMapper employeesMapper;

    // 获取所有emp的方法
    @Override
    public List<Employees> getAllEmp() {
        // ① 创建example
        EmployeesExample employeesExample = new EmployeesExample();
        // ② 设置参数(设置的是where条件后面的参数)
        EmployeesExample.Criteria criteria = employeesExample.createCriteria();
        criteria.andDepartmentIdIsNotNull();
        // ③ 执行查询
        return employeesMapper.selectByExample(employeesExample);
    }


    // 根据主键获取Emp
    @Cacheable(key = "#root.methodName + '_' + #a0")
    @Override
    public Employees getEmpById(Integer id) {
        return employeesMapper.selectByPrimaryKey(id);
    }

}
