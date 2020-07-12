package com.hao.springboot.mybatis.mapper;

import com.hao.springboot.mybatis.po.Employee;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeMapperTest {

    @Autowired
    EmployeeMapper empMapper;

    @Test
    public void getAllEmployees() {
        List<Employee> allEmployees = empMapper.getAllEmployees();
        assertEquals(14, allEmployees.size());
        System.err.println("allEmployees: " + allEmployees);
    }

}