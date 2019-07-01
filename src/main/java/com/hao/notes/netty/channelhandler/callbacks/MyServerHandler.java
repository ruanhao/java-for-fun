package com.hao.notes.netty.channelhandler.callbacks;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /*
         * channelReadComplete() is triggered once there is no more data to read from the underlying transport.
         * Refs:
         *   - https://stackoverflow.com/questions/28473252/how-does-netty-determine-when-a-read-is-complete
         *   - https://www.infoq.cn/article/WV30iLpz_fYsDY8dpuyY
         */
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
        super.handlerRemoved(ctx);
    }

}
