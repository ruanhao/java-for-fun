package com.hao.spring.cloud.license;

import com.hao.spring.cloud.license.context.UserContextInterceptor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.client.RestTemplate;


/**
 * 先启动 Zuul ，再启动 LicenseService
 * 执行 nc -l 9999
 * 执行 curl 'localhost:8761/api/license/hello'
 * 在 9999 端口可以观察到：
 * POST / HTTP/1.1
 * tmx-correlation-id: 164ddae6-746f-4c1c-9480-64173cc210b8
 * tmx-auth-token:
 * User-Agent: Java/1.8.0_211
 * Host: localhost:9999
 * Connection:keep-alive
 * Content-type:application/x-www-form-urlencoded
 * Content-Length:0
 */

@SpringBootApplication
public class LicenseServiceApplication implements CommandLineRunner {

    public static final EventLoopGroup BOSS = new NioEventLoopGroup(1, new DefaultThreadFactory("acceptor"));
    public static final EventLoopGroup WORKER = new NioEventLoopGroup(4, new DefaultThreadFactory("worker"));


    public static void main(String[] args) {
        SpringApplication.run(LicenseServiceApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate template = new RestTemplate();
        List interceptors = template.getInterceptors();
        if (interceptors==null){
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        }
        else{
            interceptors.add(new UserContextInterceptor());
            // template.setInterceptors(interceptors);
        }
        return template;
    }

    @Override
    public void run(String... args) throws Exception {
        ServerBootstrap callHomeServerTcpBootstrap = new ServerBootstrap();
        callHomeServerTcpBootstrap.group(BOSS, WORKER);
        callHomeServerTcpBootstrap.channel(NioServerSocketChannel.class);
        callHomeServerTcpBootstrap
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    public void initChannel(final NioSocketChannel channel) throws Exception {
                        ChannelPipeline p = channel.pipeline();
                        p.addLast(new HttpServerCodec());
                        p.addLast(new HttpObjectAggregator(65535));
                        p.addLast(new ChunkedWriteHandler());
                        p.addLast(new MyHandler());
                    }
                });
        callHomeServerTcpBootstrap.bind(9999);
    }
}

class MyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        System.err.println(request);
        System.err.println();
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
        ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
