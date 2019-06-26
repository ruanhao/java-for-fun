package com.hao.notes.netty.handlers.delimiterbasedframe;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.hao.notes.utils.NettyUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;

public class Examples {

    private static final int PORT = 40839;

    @Test
    @SneakyThrows
    public void testLineDelimiter() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new MyServerChannelInitializer());
            ChannelFuture channelFuture = b.bind(PORT).sync();
            NettyUtils.connectTo("localhost", PORT, new MyClientChannelInitializer());
            TimeUnit.SECONDS.sleep(3L);
            channelFuture.channel().close();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
