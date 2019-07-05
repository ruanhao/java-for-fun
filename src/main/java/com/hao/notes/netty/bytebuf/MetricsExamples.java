package com.hao.notes.netty.bytebuf;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocatorMetric;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocatorMetric;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.SneakyThrows;

public class MetricsExamples {

    @Test
    public void testUnpooledHeapBufferMetrics() {
        String str = "hello world";
        ByteBufAllocatorMetric metric = UnpooledByteBufAllocator.DEFAULT.metric();
        System.err.println("metric0: " + metric);
        assertEquals(0, metric.usedHeapMemory());

        ByteBuf buf = Unpooled.copiedBuffer(str, Charsets.UTF_8);
        System.err.println("metric1: " + metric);
        assertEquals(str.length() * 3, metric.usedHeapMemory());

        buf.release();
        System.err.println("metric2: " + metric);
        assertEquals(0, metric.usedHeapMemory());
    }


    @Test
    public void testPooledHeapBufferMetrics() {
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        PooledByteBufAllocatorMetric metric = (PooledByteBufAllocatorMetric) alloc.metric();
        int randomNum = new Random().nextInt(10) + 1;
        System.err.println(randomNum);
        int chunkSize = metric.chunkSize();

        System.err.println("metric0: " + metric);
        assertEquals(0, metric.usedHeapMemory());

        ByteBuf buf0 = alloc.heapBuffer(randomNum * chunkSize);
        int randomBytesNum = new Random().nextInt(1024) + 64;
        System.err.println(randomBytesNum);
        ByteBuf buf1 = alloc.heapBuffer(randomBytesNum);
        System.err.println("metric1: " + metric);
        assertEquals((randomNum + 1) * chunkSize, metric.usedHeapMemory()); // round 16M

        buf0.release();
        System.err.println("metric2: " + metric);
        assertEquals(chunkSize, metric.usedHeapMemory());

        buf1.release();
        System.err.println("metric3: " + metric);
        assertEquals(chunkSize, metric.usedHeapMemory());
    }


    @Test
    public void testPooledDirectBufferMetrics() {
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        PooledByteBufAllocatorMetric metric = (PooledByteBufAllocatorMetric) alloc.metric();
        int randomNum = new Random().nextInt(10) + 1;
        System.err.println(randomNum);
        int chunkSize = metric.chunkSize();

        System.err.println("metric0: " + metric);
        assertEquals(0, metric.usedDirectMemory());

        ByteBuf buf0 = alloc.directBuffer(randomNum * chunkSize);
        int randomBytesNum = new Random().nextInt(1024) + 64;
        System.err.println(randomBytesNum);
        ByteBuf buf1 = alloc.directBuffer(randomBytesNum);
        System.err.println("metric1: " + metric);
        assertEquals((randomNum + 1) * chunkSize, metric.usedDirectMemory()); // round 16M

        buf0.release();
        System.err.println("metric2: " + metric);
        assertEquals(chunkSize, metric.usedDirectMemory());

        buf1.release();
        System.err.println("metric3: " + metric);
        assertEquals(chunkSize, metric.usedDirectMemory());
    }

    @Test
    @SneakyThrows
    public void testPooledThreadLocalCaches() {
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        PooledByteBufAllocatorMetric metric = (PooledByteBufAllocatorMetric) alloc.metric();
        int randomThreadNum = new Random().nextInt(8) + 1;
        EventLoopGroup group = new NioEventLoopGroup(randomThreadNum);
        System.out.println(randomThreadNum);
        System.out.println(metric.chunkSize());
        System.out.println("metric0: " + metric);
        for (int i = 0; i < randomThreadNum; i++) {
            group.execute(() -> {
                alloc.heapBuffer(64);
            });
        }
        TimeUnit.SECONDS.sleep(1L);
        System.out.println("metric1: " + metric);
        assertEquals(randomThreadNum, metric.numThreadLocalCaches());
        assertEquals(randomThreadNum * metric.chunkSize(), metric.usedHeapMemory());

        group.execute(() -> {
            alloc.heapBuffer(64);
        });
        TimeUnit.SECONDS.sleep(1L);
        System.out.println("metric2: " + metric);
        assertEquals(randomThreadNum * metric.chunkSize(), metric.usedHeapMemory()); // no change
    }

    @Test
    public void testUnpooledDirectBufferMetrics() {
        int randomNum = new Random().nextInt(1024) + 1;
        ByteBufAllocatorMetric metric = UnpooledByteBufAllocator.DEFAULT.metric();
        System.err.println("metric0: " + metric);
        assertEquals(0, metric.usedDirectMemory());

        ByteBuf buf = Unpooled.directBuffer(randomNum);
        System.err.println("metric1: " + metric);
        assertEquals(randomNum, metric.usedDirectMemory());

        buf.release();
        System.err.println("metric2: " + metric);
        assertEquals(0, metric.usedDirectMemory());

    }
}
