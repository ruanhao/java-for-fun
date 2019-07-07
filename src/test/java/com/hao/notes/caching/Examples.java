package com.hao.notes.caching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class Examples {

    @Autowired
    MyCache myCache;

    @Autowired
    MyCache2 myCache2;

    @Test
    public void testReadingCacheWithDefaultKey() {
        assertEquals(myCache.generate0(1), myCache.generate0(1));
        assertEquals(myCache.generate0(2), myCache.generate0(2));
        assertNotEquals(myCache.generate0(1), myCache.generate0(2));
    }


    @Test
    public void testReadingCacheWithSpEL() {
        // Can check key in org.springframework.cache.interceptor.CacheAspectSupport.findCachedItem(Collection<CacheOperationContext>)
        assertEquals(myCache.generate1(1), myCache.generate1(1));
        assertEquals(myCache.generate1(2), myCache.generate1(2));
        assertNotEquals(myCache.generate1(1), myCache.generate1(2));
    }


    @Test
    public void testReadingCacheWithKeyGenerator() {
        // Can check key in org.springframework.cache.interceptor.CacheAspectSupport.findCachedItem(Collection<CacheOperationContext>)
        assertEquals(myCache.generate2(1, 2), myCache.generate2(1, 2));
        assertEquals(myCache.generate2(3, 4), myCache.generate2(3, 4));
        assertNotEquals(myCache.generate2(1, 2), myCache.generate2(3, 4));
    }

    @Test
    public void testReadingCacheWithCondition() {
        // Can check key in org.springframework.cache.interceptor.CacheAspectSupport.findCachedItem(Collection<CacheOperationContext>)
        assertNotEquals(myCache.generate3(1), myCache.generate3(1));
        assertNotEquals(myCache.generate3(2), myCache.generate3(2));
        assertEquals(myCache.generate3(3), myCache.generate3(3));
    }

    @Test
    public void testUpdateCacheWithDefaultKey() {
        int origin = myCache.generate0(1);
        int randomInt = new Random().nextInt(1024);
        myCache.update0(1, randomInt);
        int newValue = myCache.generate0(1);
        assertEquals(randomInt, newValue);
        assertNotEquals(origin, newValue);
        assertEquals(randomInt, myCache.generate0(1));
    }


    @Test
    public void testDeleteCacheWithDefaultKey() {
        int origin = myCache.generate0(1);
        myCache.delete0(1);
        int newValue = myCache.generate0(1);
        assertNotEquals(origin, newValue);
    }

    @Test
    public void testDeleteAllCacheEntrisWithDefaultKey() {
        int origin1 = myCache.generate0(1);
        int origin2 = myCache.generate0(2);
        myCache.delete0AllEntries();
        assertNotEquals(origin1, myCache.generate0(1));
        assertNotEquals(origin2, myCache.generate0(2));
    }

    @Test
    /*
     * 无论删除缓存操作是否出现异常，缓存都将清除
     */
    public void testDeleteCacheBeforeInvocationWithDefaultKey() {
        int origin1 = myCache.generate0(1);
        try {
            myCache.delete0BeforeInvocation(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotEquals(origin1, myCache.generate0(1));
    }

    @Test
    /*
     * 提取公共配置
     */
    public void testCacheConfig() {
        // read
        assertEquals(myCache2.generate0(1), myCache2.generate0(1));
        assertEquals(myCache2.generate0(2), myCache2.generate0(2));
        assertNotEquals(myCache2.generate0(1), myCache2.generate0(2));
        // delete
        int origin = myCache2.generate0(1);
        myCache2.delete0(1);
        int newValue = myCache2.generate0(1);
        assertNotEquals(origin, newValue);
        // update
        origin = myCache2.generate0(1);
        int randomInt = new Random().nextInt(1024);
        myCache2.update0(1, randomInt);
        newValue = myCache2.generate0(1);
        assertEquals(randomInt, newValue);
        assertNotEquals(origin, newValue);
        assertEquals(randomInt, myCache2.generate0(1));
    }

    /*
     * 组合注解
     */
    @Test
    public void testCachingAnnotation() {
        int value = myCache.generate4(1);
        int value2 = myCache.generate4(1);
        // 因为组合注解中有 CachePut 注解，因此方法一定会被执行，即不会读取已有缓存
        assertNotEquals(value, value2);
        assertEquals(value2, myCache.generate0(101));
        assertEquals(value2, myCache.generate0(1001));
    }

}
