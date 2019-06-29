package com.hao.notes.netty.channelhandler.replayingdecoder;

import org.junit.Test;

import com.hao.notes.utils.NettyUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;

public class Examples {

    private static final int PORT = 40839;

    @Test
    @SneakyThrows
    public void testReplaylingDecoder() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new MyServerChannelInitializer());
            ChannelFuture channelFuture = b.bind(PORT).sync();
            NettyUtils.connectTo("localhost", PORT, new MyClientChannelInitializer());
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Test
    @SneakyThrows
    public void testReplaylingDecoderWithCheckpoint() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new MyServerCheckpointChannelInitializer());
            ChannelFuture channelFuture = b.bind(PORT).sync();
            NettyUtils.connectTo("localhost", PORT, new MyClientChannelInitializer());
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
