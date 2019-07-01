package com.hao.notes.netty.bytebuf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Examples {

    @Test
    public void testCompositeByteBuf() {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        ByteBuf headBuf = Unpooled.buffer(16);
        ByteBuf directBuf = Unpooled.directBuffer(32);
        compositeByteBuf.addComponents(headBuf, directBuf);
        compositeByteBuf.forEach(buf -> log.info("{}", buf));
    }

    @Test
    public void testSearchOperations() {
        ByteBuf buf = Unpooled.copiedBuffer("hello\0San\rJose", Charsets.UTF_8);
        assertEquals(5, buf.forEachByte(ByteProcessor.FIND_NUL));
        assertEquals(9, buf.forEachByte(ByteProcessor.FIND_CR));
    }

}
