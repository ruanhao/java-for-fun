package com.hao.notes.jvm;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;

public class JvmOptions {

    private static List<byte[]> byteList = new ArrayList<byte[]>();

    @Test
    /*
     * 基本参数：
     * -Xmx10m -Xms10m -XX:+PrintGC
     * -Xmx10m -Xms10m -XX:+PrintGCDetails
     * -Xmx10m -Xms10m -XX:+PrintHeapAtGC
     *
     * 添加时间信息：
     * -Xmx10m -Xms10m -XX:+PrintGC -XX:+PrintGCTimeStamps
     * -Xmx10m -Xms10m -XX:+PrintGC -XX:+PrintGCApplicationConcurrentTime
     * -Xmx10m -Xms10m -XX:+PrintGC -XX:+PrintGCDateStamps
     *
     * 添加输出文件：
     * -Xmx10m -Xms10m -XX:+PrintGC -XX:+PrintGCDateStamps -Xloggc:/tmp/gc_%p_%t.log
     * -Xmx1024m -Xms10m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/tmp/gc_%p_%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=8K
     *
     * 查看系统参数：
     * -Xmx1024m -Xms10m -XX:+PrintFlagsFinal
     *
     * 溢出后处理：
     * -Xmx10m -Xms10m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/jvm.dump
     * -Xmx10m -Xms10m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/jvm.dump "-XX:OnOutOfMemoryError=/tmp/collect_sys_info.sh %p"
     */
    public void oom() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            byte[] bytes = new byte[1 * 1024 * 1024];
            byteList.add(bytes);
            showMemory();
        }
    }

    private void showMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.err.println(String.format("max: %s, free: %s, total: %s",
                maxMemory, freeMemory, totalMemory));
    }

    @Test
    @SneakyThrows
    // -XX:NativeMemoryTracking=detail
    // 设置基线：jcmd pid VM.native_memory baseline
    // 检查 diff：jcmd <pid> VM.native_memory summary.diff
//    Native Memory Tracking:
//
//        Total: reserved=7070801KB +1157182KB, committed=1832717KB +1157314KB
//
//        -                 Java Heap (reserved=4194304KB, committed=262144KB)
//                                    (mmap: reserved=4194304KB, committed=262144KB)
//
//        -                     Class (reserved=1070344KB +1KB, committed=23304KB +1KB)
//                                    (classes #1982 +3)
//                                    (malloc=11528KB +1KB #1658 +31)
//                                    (mmap: reserved=1058816KB, committed=11776KB)
//
//        -                    Thread (reserved=27762KB, committed=27762KB)
//                                    (thread #28)
//                                    (stack: reserved=27648KB, committed=27648KB)
//                                    (malloc=82KB #147)
//                                    (arena=32KB #50)
//
//        -                      Code (reserved=250115KB +29KB, committed=4027KB +161KB)
//                                    (malloc=515KB +29KB #1319 +65)
//                                    (mmap: reserved=249600KB, committed=3512KB +132KB)
//
//        -                        GC (reserved=165934KB, committed=153138KB)
//                                    (malloc=12690KB #160)
//                                    (mmap: reserved=153244KB, committed=140448KB)
//
//        -                  Compiler (reserved=148KB, committed=148KB)
//                                    (malloc=18KB #69 +1)
//                                    (arena=131KB #7)
//
//        -                  Internal (reserved=1358382KB +1157120KB, committed=1358382KB +1157120KB) // 可以看出 native 的增长
//                                    (malloc=1358350KB +1157120KB #3798 +229)
//                                    (mmap: reserved=32KB, committed=32KB)
//
//        -                    Symbol (reserved=3251KB +2KB, committed=3251KB +2KB)
//                                    (malloc=1932KB +2KB #6045 +40)
//                                    (arena=1319KB #1)
//
//        -    Native Memory Tracking (reserved=366KB +30KB, committed=366KB +30KB)
//                                    (malloc=129KB +20KB #1823 +275)
//                                    (tracking overhead=236KB +10KB)
//
//        -               Arena Chunk (reserved=196KB, committed=196KB)
//                                    (malloc=196KB)
    public void showDirectMemory() {
        while (true) {
            System.out.println("Allocating 5M ...");
            Unpooled.directBuffer(1 * 1024 * 1024 * 5);
            Thread.sleep(1000L);
        }
    }




}