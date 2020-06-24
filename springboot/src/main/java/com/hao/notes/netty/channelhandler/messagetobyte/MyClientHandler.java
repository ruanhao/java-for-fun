package com.hao.notes.netty.channelhandler.messagetobyte;

import java.util.Random;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.info("[Client] <= {}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        long value = new Random().nextLong();
        log.info("[Client] => {}", value);
        ctx.writeAndFlush(value);
    }

}
