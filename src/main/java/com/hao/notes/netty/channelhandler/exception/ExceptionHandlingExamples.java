package com.hao.notes.netty.channelhandler.exception;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.hao.notes.utils.NettyUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;

public class ExceptionHandlingExamples {

    
    @Test
    @SneakyThrows
    public void testExceptionHandling() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new MyChannelInitializer());
            ChannelFuture channelFuture = b.bind(40839).sync();
            NettyUtils.connect().writeAndFlush(Unpooled.copiedBuffer("hello", Charsets.UTF_8));
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
