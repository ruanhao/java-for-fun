package com.hao.notes.netty.channelhandler.callbacks;

import static com.hao.notes.utils.NettyUtils.close;
import static com.hao.notes.utils.NettyUtils.connectTo;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Charsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;

public class Examples {

    private static final int PORT = 40839;

    @Test
    @SneakyThrows
    public void testHandlerCallbacks() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MyServerHandler());
                        }
                    });
            b.bind(40840);
            ChannelFuture channelFuture = b.bind(PORT).sync();

            Channel client = connectTo("localhost", PORT);
            client.writeAndFlush(Unpooled.copiedBuffer("Hello World", Charsets.UTF_8));
            // client.writeAndFlush(Unpooled.copiedBuffer("Hey Man", Charsets.UTF_8));
            close(client);
            TimeUnit.SECONDS.sleep(999L);
            channelFuture.channel().close();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
