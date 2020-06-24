package com.hao.notes.netty.channelhandler.replayingdecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new MyLongToByteEncoder());
        ch.pipeline().addLast(new MyServerByteToLongDecoder());
        ch.pipeline().addLast(new MyServerHandler());
    }


}
