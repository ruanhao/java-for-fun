package com.hao.springboot.mybatis.mapper;

import com.hao.springboot.mybatis.vo.DeptNoAvgSalGrade;
import com.hao.springboot.mybatis.vo.EmpDptSal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PowerNodeExercisesMapper {
    List<EmpDptSal> getEmpsWithHighestSalInDept();
    List<DeptNoAvgSalGrade> getAvgSalGradeGroupByDept();
}
