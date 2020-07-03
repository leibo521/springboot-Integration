package com.mybatismbg.testmybatismbg.controller;

import com.mybatismbg.testmybatismbg.bean.Employees;
import com.mybatismbg.testmybatismbg.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpController {

    /*
    * 构建restfulApi:
    *   查询:GET
    *   添加:POST
    *   更新(全部更新):PUT
    *   更新(局部更新):PATCH
    *   删除:DELETE
    * */

    // 注入service
    @Autowired
    EmpService empService;

    @GetMapping("/emps")
    public List<Employees> getAllEmp(){
        return empService.getAllEmp();
    }

    @GetMapping("/{id}")
    public Employees getEmpById(@PathVariable("id") Integer empId){
        return empService.getEmpById(empId);
    }

    @PostMapping("/Emp")
    public Employees creatEmp(@RequestBody Employees employees){
        System.out.println(employees);
        return employees;
    }

}
