package com.hao.springboot.mybatis.mapper;

import com.hao.springboot.mybatis.vo.DeptNoAvgSalGrade;
import com.hao.springboot.mybatis.vo.EmpDptSal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 *
 * 子查询：<br/>
 * {@link PowerNodeExercisesMapperTest#exercise01()} 每个部门最高薪水的人员名称 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise02()} 薪水在部门平均工资之上的人员名称 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise08()} 查出比普通员工最高公司还高的领导 <br/>
 *
 * 分组 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise03()} 部门中所有人平均的薪水等级 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise05()} 平均薪水最高的部门编号 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise06()} 平均薪水最高的部门名称 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise07()} 平均薪水的等级最低的部门名称 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise12()} 每个薪水等级员工数量 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise17()} 至少有 5 个员工的所有部门(having 用法) <br/>
 * {@link PowerNodeExercisesMapperTest#exercise26()} 按部门计算服务年限平均值 <br/>
 *
 * 自连接 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise04()} 不用 max 取得最高薪水 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise19()} 列出所有职务为 CLERK 的员工姓名和所属部门人数 <br/>
 *
 * 外连接 <br/>
 * {@link PowerNodeExercisesMapperTest#exercise16()} 列出部门和员工信息，包括没有员工的部门 <br/>
 *
 *
 *

 */

@SpringBootTest
class PowerNodeExercisesMapperTest {

    @Autowired
    PowerNodeExercisesMapper mapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void exercise19() {
        String sql = "SELECT E1.ENAME, E1.DEPTNO, count(E2.ENAME)\n" +
                "FROM EMP E1\n" +
                "JOIN EMP E2\n" +
                "WHERE E1.JOB = 'CLERK' and E2.DEPTNO = E1.DEPTNO\n" +
                "GROUP BY E1.ENAME, E1.DEPTNO";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        _pp(result);
        assert result.size() == 4;
    }

    @Test
    public void exercise26() {
        String sql = "SELECT DEPTNO, avg(TIMESTAMPDIFF(YEAR, HIREDATE, now()))\n" +
                "FROM EMP\n" +
                "GROUP BY DEPTNO\n";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        _pp(result);
    }

    @Test
    public void exercise17() {
        String sql = "SELECT E.DEPTNO\n" +
                "FROM EMP E\n" +
                "GROUP BY E.DEPTNO\n" +
                "having count(*) >= 5\n"; // having 后可以使用聚合函数
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        _pp(result);
        assert result.size() == 2;
    }

    @Test
    public void exercise16() {
        String sql = "SELECT D.DNAME, E.ENAME\n" +
                "FROM DEPT D\n" +
                "LEFT JOIN EMP E on D.DEPTNO = E.DEPTNO";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        _pp(result);

    }

    @Test
    public void exercise12() {
        String sql = "SELECT GRADE, count(*) as total\n" +
                "FROM EMP\n" +
                "JOIN SALGRADE\n" +
                "ON SAL between LOSAL and HISAL\n" +
                "GROUP BY GRADE";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        _pp(result);
        result.forEach(map -> {
            int grade = ((Integer) map.get("GRADE"));
            long total = ((long) map.get("total"));
            if (grade == 3) {
                assert total == 2;
            }
        });
    }

    @Test
    public void exercise08() {
        String sql = "SELECT ENAME\n" +
                "FROM EMP\n" +
                "WHERE SAL > (SELECT max(SAL)\n" + // 比普通员工最高薪水还高的必定是领导
                "                                 FROM EMP\n" +
                "                                 WHERE EMPNO not in (SELECT distinct MGR\n" +
                "                                                     FROM EMP\n" +
                "                                                     WHERE MGR is not NULL))";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        assert result.size() == 6;
    }

    @Test
    public void exercise07() {
        String sql = "SELECT D.DEPTNO, D.DNAME, avg(SAL) as avgsal\n" +
                "FROM EMP\n" +
                "JOIN DEPT D on EMP.DEPTNO = D.DEPTNO\n" +
                "GROUP BY D.DEPTNO\n" +
                "ORDER BY avgsal\n" +
                "LIMIT 1;";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        assert result.get(0).get("DNAME").equals("SALES");
    }

    @Test
    public void exercise06() {
        String sql = "SELECT D.DNAME, avg(E1.SAL) as avgSal\n" +
                "FROM EMP E1\n" +
                "JOIN DEPT D on E1.DEPTNO = D.DEPTNO\n" +
                "GROUP BY D.DNAME\n" +
                "ORDER BY avgSal DESC\n" +
                "LIMIT 1;\n";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        assert result.get(0).get("DNAME").equals("ACCOUNTING");

    }

    @Test
    public void exercise05() {
        String sql = "SELECT E1.DEPTNO, avg(E1.SAL) as avgSal\n" +
                "FROM EMP E1\n" +
                "GROUP BY E1.DEPTNO\n" +
                "ORDER BY avgSal DESC\n" +
                "LIMIT 1;";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        _pp(result);
    }

    @Test
    public void exercise04() {
        String sql = "SELECT E1.ENAME, E1.SAL\n" +
                "FROM EMP E1\n" +
                "LEFT JOIN EMP E2\n" +
                "ON E2.SAL > E1.SAL\n" +
                "WHERE E2.SAL is null\n";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        assert result.size() == 1;
        assert (double) result.get(0).get("SAL") == 5000;
    }

    @Test
    public void exercise01() {
        List<EmpDptSal> result = mapper.getEmpsWithHighestSalInDept();
        assert 4 == result.size();
        Optional<EmpDptSal> scottOpt =
                result.stream().filter(e -> e.getEname().equals("SCOTT")).findAny();
        assert scottOpt.isPresent();
        assert scottOpt.get().getSal() == 3000;
    }

    @Test
    public void exercise02() {
        String sql = "SELECT E.ENAME, E.SAL\n" +
                "FROM (\n" +
                "         SELECT DEPTNO, avg(SAL) as avgSal\n" +
                "         FROM EMP\n" +
                "         GROUP BY DEPTNO\n" +
                "    ) T\n" +
                "JOIN EMP E\n" +
                "ON E.SAL > T.avgSal and E.DEPTNO = T.DEPTNO";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        assert result.size() == 6;
    }

    @Test
    public void exercise03() {
        List<DeptNoAvgSalGrade> result = mapper.getAvgSalGradeGroupByDept();
        for (DeptNoAvgSalGrade item : result) {
            switch (item.getDeptno()) {
                case 10:
                    assert item.getAvgSalGrade() == 3.6667;
                    break;
                case 20:
                    assert item.getAvgSalGrade() == 2.8;
                    break;
                case 30:
                    assert item.getAvgSalGrade() == 2.5;
                    break;
                default:
                    break;
            }
        }
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
