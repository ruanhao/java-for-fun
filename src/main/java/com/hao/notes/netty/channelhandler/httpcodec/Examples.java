package com.hao.notes.netty.channelhandler.httpcodec;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;

public class Examples {

    private final static int PORT = 40839;

    @Test
    @SneakyThrows
    public void testHttpServerCodec() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new MyChannelInitializer());
            ChannelFuture channelFuture = b.bind(PORT).sync();
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject("http://localhost:"+PORT, String.class);
            assertEquals("Hello World", response);
            channelFuture.channel().close();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Test
    @SneakyThrows
    public void testHttpServerCodecWithAggregator() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChannelInitializerWithAggregator());
            ChannelFuture channelFuture = b.bind(PORT).sync();
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject("http://localhost:" + PORT, String.class);
            assertEquals("Hello Shanghai", response);
            channelFuture.channel().close();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
