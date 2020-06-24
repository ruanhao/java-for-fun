package com.hao.notes.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

// 生产者消费者模式：
// 1. 判断等待 (while and wait)
// 2. 执行业务
// 3. 发出通知 (notify)
public class ProductAndConsumer {

    @SneakyThrows
    @Test
    public void testWaitAndNotify() {
        Data data = new Data();
        Set<Integer> result = new HashSet<>();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.increment());
            }
        }, "Producer1").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.increment());
            }
        }, "Producer2").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.decrement());
            }
        }, "Consumer1").start();
        TimeUnit.SECONDS.sleep(3L);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.decrement());
            }
        }, "Consumer2").start();
        TimeUnit.SECONDS.sleep(3L);
        assertEquals("Only 1 and 0", 2, result.size());
    }

    @SneakyThrows
    @Test
    public void testLockAndCondition() {
        Data2 data = new Data2();
        Set<Integer> result = new HashSet<>();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.increment());
            }
        }, "Producer1").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.increment());
            }
        }, "Producer2").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.decrement());
            }
        }, "Consumer1").start();
        TimeUnit.SECONDS.sleep(3L);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                result.add(data.decrement());
            }
        }, "Consumer2").start();
        TimeUnit.SECONDS.sleep(3L);
        assertEquals("Only 1 and 0", 2, result.size());
    }


    @Test
    @SneakyThrows
    /*
     * 通过使用多个 Condition 对象，达到线程按一定顺序执行的目的
     */
    public void testRunBySequence() {
        Data3 data = new Data3();

        new Thread(() ->  {
           IntStream.range(0, 10).forEach(i -> {
               data.runA();
           });
        }, "A").start();

        new Thread(() ->  {
            IntStream.range(0, 10).forEach(i -> {
                data.runB();
            });
         }, "B").start();

        new Thread(() ->  {
            IntStream.range(0, 10).forEach(i -> {
                data.runC();
            });
         }, "C").start();;

        TimeUnit.SECONDS.sleep(3);
    }
}


@Slf4j
class Data {
    private volatile int i = 0;

    @SneakyThrows
    public synchronized int increment() {
        while (i != 0) { // 判断
            this.wait(); // 等待
        }
        i++;             // 业务
        log.info("i: {}", i);
        this.notifyAll(); // 通知
        return i;
    }

    @SneakyThrows
    public synchronized int decrement() {
        while (i == 0) {
            this.wait();
        }
        i--;
        log.info("i: {}", i);
        this.notifyAll();
        return i;
    }
}

@Slf4j
class Data2 {
    private volatile int i = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    @SneakyThrows
    public int increment() {
        try {
            lock.lock();
            while (i != 0) {
                condition.await();
            }
            i++;
            log.info("i: {}", i);
            condition.signalAll();
            return i;
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public int decrement() {
        try {
            lock.lock();
            while (i == 0) {
                condition.await();
            }
            i--;
            log.info("i: {}", i);
            condition.signalAll();
            return i;
        } finally {
            lock.unlock();
        }
    }
}

@Slf4j
class Data3 {
    private static final int STATE_A = 0;
    private static final int STATE_B = 1;
    private static final int STATE_C = 2;

    private volatile int state = STATE_A;
    Lock lock = new ReentrantLock();
    Condition conditionA = lock.newCondition();
    Condition conditionB = lock.newCondition();
    Condition conditionC = lock.newCondition();

    @SneakyThrows
    public void runA() {
        lock.lock();
        try {
            while (state != STATE_A) {
                conditionA.await();
            }
            log.info("A is running");
            state = STATE_B;
            conditionB.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void runB() {
        lock.lock();
        try {
            while (state != STATE_B) {
                conditionB.await();
            }
            log.info("B is running");
            state = STATE_C;
            conditionC.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public void runC() {
        lock.lock();
        try {
            while (state != STATE_C) {
                conditionC.await();
            }
            log.info("C is running");
            state = STATE_A;
            conditionA.signalAll();
        } finally {
            lock.unlock();
        }
    }

}