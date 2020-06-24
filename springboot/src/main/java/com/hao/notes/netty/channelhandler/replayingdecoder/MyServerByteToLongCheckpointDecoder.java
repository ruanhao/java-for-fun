package com.hao.notes.netty.channelhandler.replayingdecoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class MyServerByteToLongCheckpointDecoder extends ReplayingDecoder<MyDecoderState> {

    private long firstValue;

    public MyServerByteToLongCheckpointDecoder() {
        // Set the initial state.
        super(MyDecoderState.READ_FIRST_VALUE);
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        switch (state()) {
        case READ_FIRST_VALUE:
            firstValue = buf.readLong();
            checkpoint(MyDecoderState.READ_SECOND_VALUE);
            // THERE SHOULD NOT BE BREAK HERE !
        case READ_SECOND_VALUE:
            long secondValue = buf.readLong();
            checkpoint(MyDecoderState.READ_FIRST_VALUE);
            out.add(firstValue + secondValue);
            break;
        default:
            throw new Error("Shouldn't reach here.");
        }

    }



}

enum MyDecoderState {
    READ_FIRST_VALUE,
    READ_SECOND_VALUE;
}
