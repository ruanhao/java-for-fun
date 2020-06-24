package com.hao.notes.netty.channelhandler.replayingdecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.info("[Server] <= {}", msg);
        long response = msg + 1;
        log.info("[Server] => {}", response);
        ctx.writeAndFlush(response);
    }

}
