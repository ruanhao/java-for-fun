package com.hao.springboot.mybatis.mapper;

import com.hao.springboot.mybatis.po.Employee;
import com.hao.springboot.mybatis.vo.DeptNoWithAvgSalGrade;
import com.hao.springboot.mybatis.vo.EmpNameWithDeptName;
import com.hao.springboot.mybatis.vo.EmpWithDeptNameAndSalGrade;
import com.hao.springboot.mybatis.vo.EmpWithMgrName;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SqlMapper {

    public int countAllEmployees();
    public int countByComm();
    public List<Employee> maxSalByJob();
    public List<EmpWithMgrName> getEmployeesWithManagerName();
    public List<EmpWithMgrName> getAllEmployeesWithManagerName();
    public List<EmpWithDeptNameAndSalGrade> getEmployeesWithDeptNameAndSalGrade();
    public List<DeptNoWithAvgSalGrade> getDeptAvgSalGrade();
    public List<EmpNameWithDeptName> getEmployeesWithDeptName();


}

