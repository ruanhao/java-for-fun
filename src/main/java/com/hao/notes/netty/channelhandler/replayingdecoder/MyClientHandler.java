package com.hao.notes.netty.channelhandler.replayingdecoder;

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
        long oneValue = new Random().nextLong();
        long anotherValue = new Random().nextLong();
        log.info("[Client] => {}", oneValue);
        log.info("[Client] => {}", anotherValue);
        ctx.writeAndFlush(oneValue);
        ctx.writeAndFlush(anotherValue);
    }

}
