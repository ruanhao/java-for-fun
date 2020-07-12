package com.hao.springboot.mybatis.mapper;

import com.hao.springboot.mybatis.po.Employee;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {

    // @Select("select * from EMP")
    List<Employee> getAllEmployees();
}
