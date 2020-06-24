package com.hao.netty.metrics;

import com.google.common.base.Charsets;
import io.netty.buffer.*;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class Metrics {

    public static void main(String[] args) {
        // _unpooledHeapBufferMetrics();
        _pooledHeapBufferMetrics();
    }


    private static void _pooledHeapBufferMetrics() {
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        PooledByteBufAllocatorMetric metric = alloc.metric();
        System.err.println("metric0: " + metric);

        int randomNum = new Random().nextInt(10) + 1;
        System.err.println("randomNum: " + randomNum);
        int chunkSize = metric.chunkSize();

        assertEquals("刚开始没有使用 heap memory",
                0, metric.usedHeapMemory());
        assertEquals("刚开始没有 ThreadLocalCache 创建",
                0, metric.numThreadLocalCaches());

        ByteBuf buf0 = alloc.heapBuffer(randomNum * chunkSize);
        assertEquals("一旦开始分配就会创建 ThreadLocalCache",
                1, metric.numThreadLocalCaches());

        int randomBytesNum = new Random().nextInt(1024) + 64;
        System.err.println("randomBytesNum: " + randomBytesNum);
        ByteBuf buf1 = alloc.heapBuffer(randomBytesNum);
        System.err.println("metric1: " + metric);
        // chunk 是以 16M 为单位进行分配
        assertEquals("新增一个 chunk",
                (randomNum + 1) * chunkSize, metric.usedHeapMemory());
        buf0.release();
        System.err.println("metric2: " + metric);
        assertEquals(chunkSize, metric.usedHeapMemory());

        buf1.release();
        System.err.println("metric3: " + metric);
        assertEquals(chunkSize, metric.usedHeapMemory());
    }


    private static void _unpooledHeapBufferMetrics() {
        String str = "hello world";
        ByteBufAllocatorMetric metric = UnpooledByteBufAllocator.DEFAULT.metric();

        // 开始
        assertEquals("Should be zero at first place",
                0, metric.usedHeapMemory());

        // 使用
        ByteBuf buf = Unpooled.copiedBuffer(str, Charsets.UTF_8);
        System.err.println("metric: " + metric);
        System.err.println("metric.usedHeapMemory(): " + metric.usedHeapMemory());
        assertEquals("Heap memory should be used",
                str.length() * 3, metric.usedHeapMemory());

        // 释放
        buf.release();
        assertEquals("Heap memory should be released",
                0, metric.usedHeapMemory());
    }
}
