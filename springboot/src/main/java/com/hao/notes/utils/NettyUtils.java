package com.hao.notes.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

public class NettyUtils {

    public static Channel connect() {
        return connectTo("localhost", 40839);
    }

    public static Channel connectTo(String ip, int port) {
        ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.err.println(ByteBufUtil.prettyHexDump(msg));
                    }
                });
            }
        };
        return connectTo(ip, port, channelInitializer);
    }

    @SneakyThrows
    public static Channel connectTo(String ip, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
            .channel(NioSocketChannel.class)
            .remoteAddress(ip, port)
            .handler(channelInitializer);
        Channel channel = b.connect().sync().channel();
        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                group.shutdownGracefully();
            }
        });
        return channel;
    }

    public static void close(Channel channel) {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
}
