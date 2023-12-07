package com.utime.household.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.utime.household.test.model.paging.Employee;
import com.utime.household.test.model.paging.Page;
import com.utime.household.test.model.paging.PageArray;
import com.utime.household.test.model.paging.PagingRequest;
import com.utime.household.test.service.EmployeeService;


@Controller
@RequestMapping("DataTable")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/")
    public String main() {
        return "test/DataTable";
    }

    @ResponseBody
    @PostMapping("Employees")
    public Page<Employee> list(@RequestBody PagingRequest pagingRequest) {
        return employeeService.getEmployees(pagingRequest);
    }

//    @PostMapping("array")
//    public PageArray array(@RequestBody PagingRequest pagingRequest) {
//        return employeeService.getEmployeesArray(pagingRequest);
//    }
    
    
}