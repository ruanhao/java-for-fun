package com.hao.notes.netty.bytebuf;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import lombok.SneakyThrows;

public class LeakDetectorExamples {


    @Test
    @SneakyThrows
    public void testParanoid() {
        ResourceLeakDetector.setLevel(Level.PARANOID);
        Set<ByteBuf> bufs = new HashSet<>();
        for (;;) {
            ByteBuf buf = Unpooled.directBuffer(1024);
            bufs.add(buf);
        }

    }
}
