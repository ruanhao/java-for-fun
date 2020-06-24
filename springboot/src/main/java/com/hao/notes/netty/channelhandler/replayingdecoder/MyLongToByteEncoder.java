package com.hao.notes.netty.channelhandler.replayingdecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long value, ByteBuf out) throws Exception {
        out.writeLong(value);
    }

}
