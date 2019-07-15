package com.hao.notes.netty.channelhandler.exception;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new MyServerHandler1());
        ch.pipeline().addLast(new MyServerHandler2());
    }

}
