package com.hao.notes.netty.channelhandler.idlestate;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new IdleStateHandler(3, 5, 10, TimeUnit.SECONDS));
        ch.pipeline().addLast(new MyServerHandler());
    }

}
