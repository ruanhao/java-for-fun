package com.hao.notes.netty.bytebuf;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
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

}
