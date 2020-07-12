package com.hao.springboot.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class MybatisApplicationTests {

    @Autowired
    DataSource ds;

    /**
     * 如果 maven 里加入了 spring-boot-starter-jdbc ，则会自动注入一个 {@link JdbcTemplate}
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @SneakyThrows
    void contextLoads() {
        Connection connection = ds.getConnection();
        assert ((DruidDataSource) ds).getCreateCount() == 5;
        System.err.println("datasource: " + ds);
        System.err.println("datasource.class: " + ds.getClass());
        System.err.println("datasource.connection: " + connection);
        System.err.println("jdbcTemplate: " + jdbcTemplate);

        List<Map<String, Object>> result = jdbcTemplate.queryForList("SELECT * FROM DEPT");
        System.err.println("SELECT * FROM DEPT: \n" + result);
        connection.close();


    }



}
