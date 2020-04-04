package com.hao.notes.jvm;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JvmLogOptions {

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




}