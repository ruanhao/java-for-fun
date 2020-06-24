package com.hao.notes.netty.channelhandler.exception;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

class MyServerHandler1 extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Caught in " + this.getClass().getName());
        super.exceptionCaught(ctx, cause);
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.err.println("Error in " + this.getClass().getName());
        throw new RuntimeException("error in " + this.getClass().getName());
    }

}
