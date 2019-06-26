package com.hao.notes.netty.handlers.lengthfieldbasedframe;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
      log.info("[Server] <= {} ({})", msg, ctx.channel().remoteAddress());
      String response = LocalDateTime.now().toString();
      ctx.writeAndFlush(response);
      log.info("[Server] => {} ({})", response, ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println(cause);
        ctx.close();
    }



}
