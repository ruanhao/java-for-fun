package com.hao.notes.netty.channelhandler.exception;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

class MyServerHandler2 extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Caught in " + this.getClass().getName());
        ctx.close();
        ctx.channel().parent().close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        
    }
    
}
