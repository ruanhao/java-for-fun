package com.hao.netty.metrics;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import lombok.Builder;
import lombok.Data;

public class MemoryUsage {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.directBuffer(1024);
        _showMemoryUsage();
        byteBuf.release();
        _showMemoryUsage();
    }

    private static  void _showMemoryUsage() {
        long usedUnpooledHeapBuffer = UnpooledByteBufAllocator.DEFAULT.metric().usedHeapMemory();
        long usedPooledHeapBuffer = PooledByteBufAllocator.DEFAULT.metric().usedHeapMemory();
        long usedUnpooledDirectBuffer = UnpooledByteBufAllocator.DEFAULT.metric().usedDirectMemory();
        long usedPooledDirectBuffer = PooledByteBufAllocator.DEFAULT.metric().usedDirectMemory();
        int numThreadLocalCaches = PooledByteBufAllocator.DEFAULT.metric().numThreadLocalCaches();
        long usedDirectMemory = PlatformDependent.usedDirectMemory();
        long maxDirectMemory = PlatformDependent.maxDirectMemory();

        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long freeHeap = (freeMemory + (maxMemory - allocatedMemory));

        MemoryUsageData usageData = MemoryUsageData.builder()
                .unpooledHeap(usedUnpooledHeapBuffer)
                .pooledHeap(usedPooledHeapBuffer)
                .unpooledDirect(usedUnpooledDirectBuffer)
                .pooledDirect(usedPooledDirectBuffer)
                .numThreadCaches(numThreadLocalCaches)
                .usedDirectMemory(usedDirectMemory)
                .maxDirectMemory(maxDirectMemory)
                .freeHeap(freeHeap)
                .build();
        System.err.println("usageData: " + usageData);
    }


}



@Data
@Builder
class MemoryUsageData {
    private long unpooledHeap;
    private long pooledHeap;
    private long unpooledDirect;
    private long pooledDirect;
    private int numThreadCaches;
    long usedDirectMemory; // currently used
    long maxDirectMemory;  // set by jvm
    long freeHeap; // amount of heap that could be used
}