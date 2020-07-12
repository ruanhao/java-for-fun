package com.hao.springboot.mybatis.po;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    Integer empno;
    String ename;
    String job;
    String mgr;
    Date hiredate;
    Double sal;
    Double comm;
    Integer deptno;
}
