package com.hao.notes.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Synchronizers {

    private volatile AtomicInteger permission;

    @Test
    @SneakyThrows
    /*
     * 类似一个减法器，归零才可以继续运行
     */
    public void testCountDownLatch() {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Counting down ...");
                latch.countDown();
            }).start();
        }

        System.out.println("Stuck here ...");
        latch.await(); // 等待计数器归零
        System.out.println("Go on working ...");
    }


    @Test
    @SneakyThrows
    /*
     * 类似一个加法器，达到指定数量才可以一起运行
     */
    public void testCyclicBarrier() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            log.info("Mission completed"); // performed by the last thread entering the barrier.
        });

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                log.info("Waiting ...");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                log.info("Running...");
            }).start();
        }

        TimeUnit.SECONDS.sleep(5L);
    }

    @Test
    @SneakyThrows
    public void testSemaphore() {
        Semaphore semaphore = new Semaphore(3);
        this.permission = new AtomicInteger(3);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach(i -> {
            pool.submit(() -> {
                try {
                    log.info("Acuiring ...");
                    semaphore.acquire();
                    log.info("Accquired successfully: {}", permission.decrementAndGet());
                    TimeUnit.SECONDS.sleep(2L);
                } catch (Exception e) {
                  e.printStackTrace();
                } finally {
                    semaphore.release();
                    log.info("Released: {}", permission.incrementAndGet());
                }
            });
        });
        TimeUnit.SECONDS.sleep(20L);
    }

}
