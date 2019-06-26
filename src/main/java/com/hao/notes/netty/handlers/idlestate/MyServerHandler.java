package com.hao.notes.netty.handlers.idlestate;


import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
            case READER_IDLE:
                log.info("Read Idle @ {}", LocalDateTime.now());
                break;
            case WRITER_IDLE:
                log.info("Write Idle @ {}", LocalDateTime.now());
                break;
            case ALL_IDLE:
                log.info("All Idle @ {}", LocalDateTime.now());
                break;
            default:
                break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }



}
