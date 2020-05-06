package com.hao.notes.jvm;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.google.common.base.Charsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("restriction")
public class AQSStudy {


    private static final sun.misc.Unsafe UNSAFE;
    private static final long TID_OFFSET;
    static {
        try {
            Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            // unsafe = (Unsafe) f.get(null);
            UNSAFE = (sun.misc.Unsafe) f.get(null);
            // UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> tk = Thread.class;
            TID_OFFSET = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("tid"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private final static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final static EventLoopGroup workerGroup = new NioEventLoopGroup();

    ReentrantLock lock = new ReentrantLock(true);
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    public static volatile boolean worker1ShouldStop = false;
    public static volatile boolean worker2ShouldStop = false;
    public static volatile boolean worker3ShouldStop = false;

    public static volatile boolean writerShouldStop = false;
    public static volatile boolean reader1ShouldStop = false;
    public static volatile boolean reader2ShouldStop = false;


    static final long getThreadId() {
        long tid = UNSAFE.getLongVolatile(Thread.currentThread(), TID_OFFSET);
        log.info("{} => {}", Thread.currentThread(), tid);
        return tid;
    }


    @SneakyThrows
    public void writer() {
        log.info("Start to write (tid: {})", getThreadId());
        // log.info("Start to write");
        WriteLock wl = rwLock.writeLock();
        wl.lock();
        try {
            while (!writerShouldStop) {
                log.info("Writing");
                TimeUnit.SECONDS.sleep(10L);
            }
        } finally {
            log.info("Stop writing");
            wl.unlock();
        }
    }


    @SneakyThrows
    public void reader1() {
        log.info("Start to read1 (tid: {})", getThreadId());
        // log.info("Start to read1");
        ReadLock rl = rwLock.readLock();
        rl.lock();
        try {
            while (!reader1ShouldStop) {
                log.info("Reading1");
                TimeUnit.SECONDS.sleep(10L);
            }
        } finally {
            log.info("Stop reading1");
            rl.unlock();
        }
    }


    @SneakyThrows
    public void reader2() {
        log.info("Start to read2 (tid: {})", getThreadId());
        ReadLock rl = rwLock.readLock();
        rl.lock();
        Thread rt2 = Thread.currentThread();
        System.out.println(rt2);
        try {
            while (!reader2ShouldStop) {
                log.info("Reading2");
                TimeUnit.SECONDS.sleep(10L);
            }
        } finally {
            log.info("Stop reading2");
            rl.unlock();
        }
    }

    @SneakyThrows
    public void worker1() {
        log.info("Worker1 started working");
        lock.lock();
        try {
            while (!worker1ShouldStop) {
                log.info("Worker1 is working");
                TimeUnit.SECONDS.sleep(10L);
            }
        } finally {
            log.info("Worker1 stopped");
            lock.unlock();
        }
    }

    @SneakyThrows
    public void worker2() {
        log.info("Worker2 started working");
        lock.lock();
        try {
            while (!worker2ShouldStop) {
                log.info("Worker2 is working");
                TimeUnit.SECONDS.sleep(10L);
            }
        } finally {
            log.info("Worker2 stopped");
            lock.unlock();
        }
    }

    @SneakyThrows
    public void worker3() {
        log.info("Worker3 started working");
        lock.lock();
        try {
            while (!worker3ShouldStop) {
                log.info("Worker3 is working");
                TimeUnit.SECONDS.sleep(10L);
            }
        } finally {
            log.info("Worker3 stopped");
            lock.unlock();
        }
    }

    @SneakyThrows
    public static void startDebugServer() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new MyServerChannelInitializer());
        b.bind(40839).sync();
    }

    public static void main(String[] args) {
        startDebugServer();
        // testReentrantLock();
        testReentrantRWLock();

    }


    @SneakyThrows
    private static void testReentrantRWLock() {
        AQSStudy aqs = new AQSStudy();

        //

        TimeUnit.SECONDS.sleep(1L);
        new Thread(aqs::reader1).start();

        TimeUnit.SECONDS.sleep(1L);
        new Thread(aqs::reader2).start();

         TimeUnit.SECONDS.sleep(1L);
         new Thread(aqs::writer).start();

        TimeUnit.HOURS.sleep(1L);
    }


    @SneakyThrows
    private static void testReentrantLock() {
        AQSStudy aqs = new AQSStudy();

        new Thread(aqs::worker1).start();

        TimeUnit.SECONDS.sleep(1L);
        new Thread(aqs::worker2).start();

         TimeUnit.SECONDS.sleep(1L);
         new Thread(aqs::worker3).start();

        TimeUnit.HOURS.sleep(1L);
    }

}

class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                String code = msg.toString(Charsets.UTF_8).trim();
                System.err.println(code);
                switch (code) {
                case "1":
                    AQSStudy.worker1ShouldStop = true;
                    break;
                case "2":
                    AQSStudy.worker2ShouldStop = true;
                    break;
                case "3":
                    AQSStudy.worker3ShouldStop = true;
                    break;
                case "r1":
                    AQSStudy.reader1ShouldStop = true;
                    break;
                case "r2":
                    AQSStudy.reader2ShouldStop = true;
                    break;
                case "w":
                    AQSStudy.writerShouldStop = true;
                    break;
                default:
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Error code\n", Charsets.UTF_8));
                    return;
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("Received\n", Charsets.UTF_8));
            }
        });
    }

}
