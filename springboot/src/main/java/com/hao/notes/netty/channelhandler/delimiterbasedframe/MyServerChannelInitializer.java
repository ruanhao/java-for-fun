package com.hao.notes.netty.channelhandler.delimiterbasedframe;

import com.google.common.base.Charsets;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        ch.pipeline().addLast(new StringDecoder(Charsets.UTF_8));
        ch.pipeline().addLast(new StringEncoder(Charsets.UTF_8));
        ch.pipeline().addLast(new MyServerHandler());
    }

}
