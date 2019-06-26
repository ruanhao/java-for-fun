package com.hao.notes.netty.handlers.lengthfieldbasedframe;

import com.google.common.base.Charsets;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
        ch.pipeline().addLast(new LengthFieldPrepender(4));
        ch.pipeline().addLast(new StringDecoder(Charsets.UTF_8));
        ch.pipeline().addLast(new StringEncoder(Charsets.UTF_8));
        ch.pipeline().addLast(new MyClientHandler());
    }

}
