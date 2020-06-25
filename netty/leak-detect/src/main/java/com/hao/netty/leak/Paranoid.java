package com.hao.netty.leak;

import io.netty.buffer.Unpooled;
import io.netty.util.ResourceLeakDetector;

public class Paranoid {
    public static void main(String[] args) {
        _paranoid();
        /*
        Jun 24, 2020 11:49:41 PM io.netty.util.ResourceLeakDetector reportTracedLeak
        SEVERE: LEAK: ByteBuf.release() was not called before it's garbage-collected. See https://netty.io/wiki/reference-counted-objects.html for more information.
        Recent access records:
        Created at:
        io.netty.buffer.UnpooledByteBufAllocator.newDirectBuffer(UnpooledByteBufAllocator.java:96)
        io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:187)
        io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:178)
        io.netty.buffer.Unpooled.directBuffer(Unpooled.java:125)
        com.hao.netty.leak.Paranoid._paranoid(Paranoid.java:13)
        com.hao.netty.leak.Paranoid.main(Paranoid.java:8)
        */
    }
    private static void _paranoid() {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        while (true) {
            Unpooled.directBuffer(1024);
        }
    }
}
