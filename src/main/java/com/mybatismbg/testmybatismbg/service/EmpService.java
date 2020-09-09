package com.mybatismbg.testmybatismbg.service;

import com.mybatismbg.testmybatismbg.bean.Employees;

import java.util.List;

public interface EmpService {
    // 查询到所有的员工
    List<Employees> getAllEmp();

    // 根据员工的id查询到员工
    Employees getEmpById(Integer id);
}
