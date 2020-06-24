package com.hao.notes.netty.channelhandler.replayingdecoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MyClientByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= Long.BYTES) {
            out.add(in.readLong());
        }
        // 如果 in 中还有数据没有读完，则该数据将包含在下一次 decode 调用的 in 参数中
    }

    @Override
    protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        assert ! ctx.channel().isActive();
        System.err.println("Channel is inactive, but there are leftover bytes not read yet: ");
        System.err.println(ByteBufUtil.prettyHexDump(in));
    }

}
