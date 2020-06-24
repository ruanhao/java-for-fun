package com.hao.notes.netty.channelhandler.lengthfieldbasedframe;

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
    public void testLengthBasedFrameDecoder() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new MyServerChannelInitializer());
            ChannelFuture channelFuture = b.bind(PORT).sync();
            NettyUtils.connectTo("localhost", PORT, new MyClientChannelInitializer());
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
