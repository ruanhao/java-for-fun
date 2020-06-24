package com.hao.notes.netty.bytebuf;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Charsets;
import com.hao.notes.utils.NettyUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocatorMetric;
import io.netty.buffer.PoolArenaMetric;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocatorMetric;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.PlatformDependent;
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
        PooledByteBufAllocatorMetric metric = alloc.metric();
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
        PooledByteBufAllocatorMetric metric = alloc.metric();
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

    private static Map<String, Long> metricsOfPoolArena(PoolArenaMetric poolArenaMetric) {
        Map<String, Long> metrics = new HashMap<>();
        metrics.put("numThreadCaches", Integer.valueOf(poolArenaMetric.numThreadCaches()).longValue());

        metrics.put("numAllocations", poolArenaMetric.numAllocations());
        metrics.put("numDeallocations", poolArenaMetric.numDeallocations());
        metrics.put("numActiveAllocations", poolArenaMetric.numActiveAllocations());

        metrics.put("numTinyAllocations", poolArenaMetric.numTinyAllocations());
        metrics.put("numTinyDeallocations", poolArenaMetric.numTinyDeallocations());
        metrics.put("numActiveTinyAllocations", poolArenaMetric.numActiveTinyAllocations());

        metrics.put("numSmallAllocations", poolArenaMetric.numSmallAllocations());
        metrics.put("numSmallDeallocations", poolArenaMetric.numSmallDeallocations());
        metrics.put("numActiveSmallAllocations", poolArenaMetric.numActiveSmallAllocations());

        metrics.put("numNormalAllocations", poolArenaMetric.numNormalAllocations());
        metrics.put("numNormalDeallocations", poolArenaMetric.numNormalDeallocations());
        metrics.put("numActiveNormalAllocations", poolArenaMetric.numActiveNormalAllocations());

        metrics.put("numHugeAllocations", poolArenaMetric.numHugeAllocations());
        metrics.put("numHugeDeallocations", poolArenaMetric.numHugeDeallocations());
        metrics.put("numActiveHugeAllocations", poolArenaMetric.numActiveHugeAllocations());

        metrics.put("numActiveBytes", poolArenaMetric.numActiveBytes());

        return metrics;
    }

    private static Map<String, Map<String, Long>> getDirectPoolArenaMetrics() {
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        Map<String, Map<String, Long>> metrics = new HashMap<>();

        for (PoolArenaMetric poolArenaMetric : alloc.metric().directArenas()) {
            metrics.put("DirectArena@" + poolArenaMetric.hashCode(), metricsOfPoolArena(poolArenaMetric));
        }

        return metrics;
    }

    @Test
    public void testPoolArenaMetrics() {
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;

        ByteBuf tinyBuf = alloc.directBuffer(1); // [1, 512)
        ByteBuf smallBuf = alloc.directBuffer(512); // [512, 8192)
        ByteBuf normalBuf = alloc.directBuffer(8192); // [8192, 16777216]
        ByteBuf hugeBuf = alloc.directBuffer(16777217); // [16777217, +inf)

        Map<String, Map<String, Long>> metrics = getDirectPoolArenaMetrics();
        Map<String, Long> metric = metrics.entrySet().stream(
                ).filter(entry -> entry.getValue().get("numThreadCaches") == 1)
                .map(Entry::getValue)
                .findAny()
                .get();

        com.hao.notes.utils.JsonUtils.printPrettyJson(metric);

        assertEquals(4, metric.get("numAllocations").longValue());
        assertEquals(0, metric.get("numDeallocations").longValue());
        assertEquals(metric.get("numAllocations").longValue() - metric.get("numDeallocations").longValue(),
                metric.get("numActiveAllocations").longValue());

        Arrays.asList("Tiny", "Small", "Normal", "Huge").forEach(type -> {
            assertEquals(1, metric.get("num" + type + "Allocations").longValue());
            assertEquals(0, metric.get("num" + type + "Deallocations").longValue());
            assertEquals(1, metric.get("numActive" + type  + "Allocations").longValue());
        });

        assertEquals(16777216 + 16777217, metric.get("numActiveBytes").longValue());
        assertEquals(alloc.metric().usedDirectMemory(), metric.get("numActiveBytes").longValue());


        /* Release */
        tinyBuf.release();
        smallBuf.release();
        normalBuf.release();
        hugeBuf.release();

        Map<String, Map<String, Long>> metrics1 = getDirectPoolArenaMetrics();
        Map<String, Long> metric1 = metrics1.entrySet().stream(
                ).filter(entry -> entry.getValue().get("numThreadCaches") == 1)
                .map(Entry::getValue)
                .findAny()
                .get();
        com.hao.notes.utils.JsonUtils.printPrettyJson(metric1);

        assertEquals(16777216, metric1.get("numActiveBytes").longValue());
        assertEquals(1, metric1.get("numHugeDeallocations").longValue());
        assertEquals(0, metric1.get("numNormalDeallocations").longValue());
        assertEquals(0, metric1.get("numSmallDeallocations").longValue());
        assertEquals(0, metric1.get("numTinyDeallocations").longValue());
        assertEquals(alloc.metric().usedDirectMemory(), metric1.get("numActiveBytes").longValue());

    }

    @Test
    @SneakyThrows
    public void testPooledThreadLocalCaches() {
        PooledByteBufAllocator alloc = PooledByteBufAllocator.DEFAULT;
        PooledByteBufAllocatorMetric metric = alloc.metric();
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

    @Test
    @SneakyThrows
    public void show_DIRECT_MEMORY_COUNTER() {
        Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
        field.setAccessible(true);
        AtomicLong value = (AtomicLong) field.get(PlatformDependent.class);
        System.out.println(value);
    }


    @Test
    @SneakyThrows
    public void monitorMetric() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
        field.setAccessible(true);
        AtomicLong counter = (AtomicLong) field.get(PlatformDependent.class);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ReferenceCountUtil.release(msg);
                        }
                    });

                }
            });
            b.bind(40839).sync();
            Channel client = NettyUtils.connectTo("localhost", 40839);
            while (true) {
                int size = 1024 * 1024 * 8; // 8M
                // ByteBuf buf = Unpooled.copiedBuffer(new byte[size]);
                ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(size);
                buf.writeBytes(new byte[size]);
                client.writeAndFlush(buf);

                long usedUnpooledHeapBuffer = UnpooledByteBufAllocator.DEFAULT.metric().usedHeapMemory();
                long usedUnpooledDirectBuffer = UnpooledByteBufAllocator.DEFAULT.metric().usedDirectMemory();

                long usedPooledHeapBuffer = PooledByteBufAllocator.DEFAULT.metric().usedHeapMemory();
                long usedPooledDirectBuffer = PooledByteBufAllocator.DEFAULT.metric().usedDirectMemory();

                int numThreadLocalCaches = PooledByteBufAllocator.DEFAULT.metric().numThreadLocalCaches();

                System.err.println(String.format("Total heap: %d [unpooled: %d, pooled: %d]",
                        usedUnpooledHeapBuffer + usedPooledHeapBuffer, usedUnpooledHeapBuffer, usedPooledHeapBuffer));
                System.err.println(String.format("Total direct: %d [unpooled: %d, pooled: %d]",
                        usedUnpooledDirectBuffer + usedPooledDirectBuffer, usedUnpooledDirectBuffer, usedPooledDirectBuffer));
                System.err.println("numThreadLocalCaches: " + numThreadLocalCaches);
                System.err.println("DIRECT_MEMORY_COUNTER: " + counter);
                System.err.println("========");

                TimeUnit.SECONDS.sleep(3L);
            }

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
