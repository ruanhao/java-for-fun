package com.hao.notes.netty.handlers.callbacks;

import static com.hao.notes.netty.Utils.close;
import static com.hao.notes.netty.Utils.connectTo;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Charsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.channelRead(ctx, msg);
                                }

                                @Override
                                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.channelRegistered(ctx);
                                }

                                @Override
                                public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.channelUnregistered(ctx);
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.channelActive(ctx);
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.channelInactive(ctx);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    /*
                                     * channelReadComplete() is triggered once there is no more data to read from the underlying transport.
                                     * Refs:
                                     *   - https://stackoverflow.com/questions/28473252/how-does-netty-determine-when-a-read-is-complete
                                     *   - https://www.infoq.cn/article/WV30iLpz_fYsDY8dpuyY
                                     */
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.channelReadComplete(ctx);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                                        throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.exceptionCaught(ctx, cause);
                                }

                                @Override
                                public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.handlerAdded(ctx);
                                }

                                @Override
                                public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                    System.err.println(new Object(){}.getClass().getEnclosingMethod().getName());
                                    super.handlerRemoved(ctx);
                                }

                            });
                        }
                    });
            ChannelFuture channelFuture = b.bind(PORT).sync();
            Channel client = connectTo("localhost", PORT);
            client.writeAndFlush(Unpooled.copiedBuffer("Hello World", Charsets.UTF_8));
            // client.writeAndFlush(Unpooled.copiedBuffer("Hey Man", Charsets.UTF_8));
            close(client);
            TimeUnit.SECONDS.sleep(1L);
            channelFuture.channel().close();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
