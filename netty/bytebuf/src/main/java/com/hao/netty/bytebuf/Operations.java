package com.hao.netty.bytebuf;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

import static org.junit.Assert.assertEquals;

public class Operations {

    public static void main(String[] args) {
        // 使用组合形式的 ByteBuf
        _compositeByteBuf();

        // ByteProcessor 的用法
        _charIndexSearch();
    }

    private static void _charIndexSearch() {
        ByteBuf buf = Unpooled.copiedBuffer("hello\0San\rJose", Charsets.UTF_8);
        assertEquals(5, buf.forEachByte(ByteProcessor.FIND_NUL));
        assertEquals(9, buf.forEachByte(ByteProcessor.FIND_CR));
    }

    private static void _compositeByteBuf() {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf headBuf = Unpooled.buffer(16);
        ByteBuf directBuf = Unpooled.directBuffer(32);
        compositeByteBuf.addComponents(headBuf, directBuf);
        // 可以看出真正的类型：
        // UnpooledSlicedByteBuf(ridx: 0, widx: 0, cap: 0/0, unwrapped: UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 0, cap: 16))
        // UnpooledSlicedByteBuf(ridx: 0, widx: 0, cap: 0/0, unwrapped: UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(ridx: 0, widx: 0, cap: 32))
        compositeByteBuf.forEach(System.err::println);
    }
}
