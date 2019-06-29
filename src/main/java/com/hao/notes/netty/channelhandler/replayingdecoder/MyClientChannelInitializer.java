package com.hao.notes.netty.channelhandler.replayingdecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new MyLongToByteEncoder());
        ch.pipeline().addLast(new MyClientByteToLongDecoder());
        ch.pipeline().addLast(new MyClientHandler());
    }


}
