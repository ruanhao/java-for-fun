package com.hao.notes.caching.redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

@TestPropertySource(properties = {
        "spring.redis.host=10.74.68.51",
        "spring.redis.port=46379",
})
public class Examples {

    @Autowired
    StringRedisTemplate stringRedisTemplate; // kv 都是字符串情况下使用

    @Autowired
    RedisTemplate<Object, Student> redisTemplate;

    @Autowired
    MyCache myCache;

    private void testRedisStringFunction() {
        String randomStr = String.valueOf(System.currentTimeMillis());
        stringRedisTemplate.opsForValue().set("msg", randomStr);
        assertEquals(randomStr, stringRedisTemplate.opsForValue().get("msg"));
    }

    private void testRedisListFunction() {
        stringRedisTemplate.opsForList().leftPush("mylist", "1");
        stringRedisTemplate.opsForList().leftPush("mylist", "2");
        assertEquals("2", stringRedisTemplate.opsForList().leftPop("mylist"));
        assertEquals("1", stringRedisTemplate.opsForList().leftPop("mylist"));
    }

    private void testObjectRedisTemplate() {
        redisTemplate.opsForValue().set("student", Student.builder().name("Peter").age(30).build());
        assertEquals("Peter", redisTemplate.opsForValue().get("student").getName());
    }

    @Test
    public void testRedisFunction() {
       testRedisStringFunction();
       testRedisListFunction();
       testObjectRedisTemplate();
    }

    @Test
    public void testCache() {
        Student std1 = myCache.generate0("Peter");
        assertEquals(std1.age, myCache.generate0("Peter").age);
        assertNotEquals(std1.age,myCache.generate0("Mary").age);

        myCache.delete0("Peter");
        assertNotEquals(std1.age, myCache.generate0("Peter").age);
        myCache.update0("Peter", 20);
        assertEquals(20, myCache.generate0("Peter").age);
    }
}

