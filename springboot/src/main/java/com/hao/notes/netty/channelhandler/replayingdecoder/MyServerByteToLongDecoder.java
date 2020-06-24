package com.hao.notes.netty.channelhandler.replayingdecoder;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class MyServerByteToLongDecoder extends ReplayingDecoder<Void> {

    private final Queue<Long> values = new LinkedList<>();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // Revert the state of the variable that might have been changed
        // since the last partial decode.
        values.clear();

        values.offer(in.readLong());
        values.offer(in.readLong());

        assert values.size() == 2;
        out.add(values.poll());
        out.add(values.poll());
    }



}
