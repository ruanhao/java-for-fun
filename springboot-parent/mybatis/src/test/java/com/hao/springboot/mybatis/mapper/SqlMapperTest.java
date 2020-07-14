package com.hao.springboot.mybatis.mapper;

import com.hao.springboot.mybatis.po.Employee;
import com.hao.springboot.mybatis.vo.DeptNoWithAvgSalGrade;
import com.hao.springboot.mybatis.vo.EmpNameWithDeptName;
import com.hao.springboot.mybatis.vo.EmpWithDeptNameAndSalGrade;
import com.hao.springboot.mybatis.vo.EmpWithMgrName;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;


/**
 * 分组函数（多行处理函数）<br/>
 * - 分组函数自动<b>忽略</b> NULL {@link SqlMapperTest#countIgnoreNull()} <br/>
 * - 分组函数不能直接用于 where 子句 {@link SqlMapperTest#doAggregationAfterGroupBy()}  <br/>
 * - 当一条语句中有 group by ，select 后<b>只能跟分组函数和参与分组的字段</b> {@link SqlMapperTest#doAggregationAfterGroupBy()}  <br/>
 * - 分组之后的数据使用如果仍然需要过滤，使用 having {@link SqlMapperTest#useHaving()}  <br/>
 *
 * 去重 <br/>
 * - distinct 只能出现在所有字段<b>最前面</b> {@link SqlMapperTest#useDistinct()}  <br/>
 *
 * 连接查询 <br/>
 * - 自连接 {@link SqlMapperTest#selfJoin()} <br/>
 * - 内连接：A 表 和 B 表无主副之分，谁前谁后无区别 <br/>
 * - 外连接：A 表 和 B 表有主副之分，主要查询主表 {@link SqlMapperTest#showAllEmpWithMgrName()} <br/>
 * - 多表连接：{@link SqlMapperTest#showEmpWithDeptAndSalGrade()} <br/>
 *
 * 子查询 <br/>
 * select 语句中嵌套 select 语句，被嵌套的 select 语句就是子查询。 <br/>
 * - 出现在 select 后的子查询 <br/>
 * - 出现在 from 后的子查询（重用）{@link SqlMapperTest#subQueryAfterFrom()} <br/>
 * - 出现在 where 后的子查询 {@link SqlMapperTest#subQueryAfterWhere()} <br/>
 *
 *
 *
 */


@SpringBootTest
class SqlMapperTest {

    @Autowired
    SqlMapper sqlMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    public void subQueryAfterSelect() {
        // 找出每个员工所在的部门名，显示员工名和部门名
        List<EmpNameWithDeptName> emps = sqlMapper.getEmployeesWithDeptName();
        _pp(emps);
        assertEquals(14, emps.size());
    }


    /**
     * 将子查询的结果当做一张临时表
     */
    @Test
    public void subQueryAfterFrom() {
        // 每个部门平均薪水工资等级
        List<DeptNoWithAvgSalGrade> deptAvgSalGrade = sqlMapper.getDeptAvgSalGrade();
        _pp(deptAvgSalGrade);
        for (DeptNoWithAvgSalGrade item : deptAvgSalGrade) {
            switch (item.getDeptno()) {
                case 10:
                case 20:
                    assertEquals(4, item.getGrade());
                    break;
                case 30:
                    assertEquals(3, item.getGrade());
                    break;
                default:
                    break;
            }
        }
    }

    @Test
    public void subQueryAfterWhere() {
        // 找出高于平均薪资的员工
        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * from EMP where SAL > (select avg(SAL) from EMP)");
        _pp(result);
        assertEquals(6, result.size());
    }

    /**
     *   <pre>
     *        from
     *          A
     *        join // A 和 B 先连接
     *          B
     *        on
     *          ..
     *        join // 上面的结果再与 C 连接
     *          C
     *        on
     *          ..
     *    </pre>
     */
    @Test
    public void showEmpWithDeptAndSalGrade() {
        // 显示员工的部门名和工资等级
        List<EmpWithDeptNameAndSalGrade> employeesWithDeptNameAndSalGrade = sqlMapper.getEmployeesWithDeptNameAndSalGrade();
        _pp(employeesWithDeptNameAndSalGrade);
    }

    @Test
    public void showAllEmpWithMgrName() {
        // 显示员工和其领导的名字 （自连接，外连接）
        List<EmpWithMgrName> employeesWithManagerName = sqlMapper.getAllEmployeesWithManagerName();
        Optional<EmpWithMgrName> kingOpt = employeesWithManagerName.stream().filter(e -> e.getEname().equals("KING")).findAny();
        assertTrue(kingOpt.isPresent(), "使用了外连接，所以能查出来");
        assertNull(kingOpt.get().getMgrName());
    }

    @Test
    public void selfJoin() {
        // 显示员工和其领导的名字 （自连接，内连接）
        List<EmpWithMgrName> employeesWithManagerName = sqlMapper.getEmployeesWithManagerName();
        _pp(employeesWithManagerName);
        Optional<EmpWithMgrName> smithOpt = employeesWithManagerName.stream().filter(e -> e.getEname().equals("SMITH")).findFirst();
        if (smithOpt.isPresent()) {
            assertEquals("FORD", smithOpt.get().getMgrName());
        } else {
            fail();
        }
        Optional<EmpWithMgrName> kingOpt = employeesWithManagerName.stream().filter(e -> e.getEname().equals("KING")).findAny();
        assertFalse(kingOpt.isPresent(), "KING 的上级是 NULL ，所以查不出来");
    }

    @Test
    public void useHaving() {
        // 显示部门平均薪资大于 2000 的数据
        String sql = "select avg(SAL) as s, DEPTNO from EMP group by DEPTNO having s > 2000";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        System.err.println("result: " + result);
        assertEquals(2, result.size());
    }

    @Test
    public void useDistinct() {
        String sql = "select distinct deptno, job from EMP";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        _pp(result);

        // 统计岗位数量
        String sql2 = "select count(distinct JOB) from EMP";
        Integer count = jdbcTemplate.queryForObject(sql2, Integer.class);
        assertEquals(5, count);
    }

    /**
     * 可以看出 count(*) 和 count(某个字段) 的区别
     */
    @Test
    public void countIgnoreNull() {
        assertEquals(14, sqlMapper.countAllEmployees());
        assertEquals(4, sqlMapper.countByComm());
    }

    @Test
    public void ruleOfSelectAfterGroupBy() {
        List<Employee> maxSalGroup = sqlMapper.maxSalByJob();
        System.err.println("maxSalGroup: " + maxSalGroup);
        assertEquals(5, maxSalGroup.size());


        try {
            // COMM 不能出现在 select 后面
            jdbcTemplate.queryForList("select JOB, COMM, max(SAL) from EMP group by JOB");
            fail("Can not be here");
        } catch (BadSqlGrammarException e) {

        }
    }

    /**
     * 分组函数在 group by 语句执行之后才会执行，<b>而 group by 在 where 之后才能执行。</b><br/>
     *
     * <pre>
     *     select     #5
     *       ..
     *     from       #1
     *       ..
     *     where      #2
     *       ..
     *     group by   #3
     *       ..
     *     having     #4
     *       ..
     *     order by   #6
     *       ..
     * </pre>
     *
     * 如果 SQL 语句中没有 group by ，则整张表自成一组。
     */
    @Test
    public void doAggregationAfterGroupBy() {

    }

    private void _pp(Collection<?> elements) {
        System.err.println("=========================");
        elements.stream().forEach(System.err::println);
    }
}

/**
 * EMP:
 *
 * +-------+--------+-----------+--------+------------+--------+--------+--------+
 * | EMPNO | ENAME  | JOB       | MGR    | HIREDATE   | SAL    | COMM   | DEPTNO |
 * +-------+--------+-----------+--------+------------+--------+--------+--------+
 * | 7369  | SMITH  | CLERK     | 7902   | 1980-12-17 |  800.0 | <null> | 20     |
 * | 7499  | ALLEN  | SALESMAN  | 7698   | 1981-02-20 | 1600.0 |  300.0 | 30     |
 * | 7521  | WARD   | SALESMAN  | 7698   | 1981-02-22 | 1250.0 |  500.0 | 30     |
 * | 7566  | JONES  | MANAGER   | 7839   | 1981-04-02 | 2975.0 | <null> | 20     |
 * | 7654  | MARTIN | SALESMAN  | 7698   | 1981-09-28 | 1250.0 | 1400.0 | 30     |
 * | 7698  | BLAKE  | MANAGER   | 7839   | 1981-05-01 | 2850.0 | <null> | 30     |
 * | 7782  | CLARK  | MANAGER   | 7839   | 1981-06-09 | 2450.0 | <null> | 10     |
 * | 7788  | SCOTT  | ANALYST   | 7566   | 1987-04-19 | 3000.0 | <null> | 20     |
 * | 7839  | KING   | PRESIDENT | <null> | 1981-11-17 | 5000.0 | <null> | 10     |
 * | 7844  | TURNER | SALESMAN  | 7698   | 1981-09-08 | 1500.0 |    0.0 | 30     |
 * | 7876  | ADAMS  | CLERK     | 7788   | 1987-05-23 | 1100.0 | <null> | 20     |
 * | 7900  | JAMES  | CLERK     | 7698   | 1981-12-03 |  950.0 | <null> | 30     |
 * | 7902  | FORD   | ANALYST   | 7566   | 1981-12-03 | 3000.0 | <null> | 20     |
 * | 7934  | MILLER | CLERK     | 7782   | 1982-01-23 | 1300.0 | <null> | 10     |
 * +-------+--------+-----------+--------+------------+--------+--------+--------+
 */

/**
 * DEPT:
 *
 * +--------+------------+----------+
 * | DEPTNO | DNAME      | LOC      |
 * +--------+------------+----------+
 * | 10     | ACCOUNTING | NEW YORK |
 * | 20     | RESEARCH   | DALLAS   |
 * | 30     | SALES      | CHICAGO  |
 * | 40     | OPERATIONS | BOSTON   |
 * +--------+------------+----------+
 */


/**
 * SALGRADE:
 *
 * +-------+-------+-------+
 * | GRADE | LOSAL | HISAL |
 * +-------+-------+-------+
 * | 1     | 700   | 1200  |
 * | 2     | 1201  | 1400  |
 * | 3     | 1401  | 2000  |
 * | 4     | 2001  | 3000  |
 * | 5     | 3001  | 9999  |
 * +-------+-------+-------+
 */
