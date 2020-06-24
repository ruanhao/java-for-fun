package com.hao.notes.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockingQueues {


    @Test
    @SneakyThrows
    /*
     * SynchronousQueue 是容量为 1 的阻塞队列
     */
    public void testSynchronousQueue() {
        BlockingQueue<String> bq = new SynchronousQueue<>();
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                try {
                    String uuid = UUID.randomUUID().toString().substring(0, 5);
                    log.info("Putting {}", uuid);
                    bq.put(uuid);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Produce-" + i).start();
        });

        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                try {
                    log.info("Get {}", bq.take());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }, "Consumer-" + i).start();
        });

        TimeUnit.HOURS.sleep(3);
    }

    @Test
    public void testBlockingQueue() {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(3);
        assertTrue(bq.add("a"));
        assertTrue(bq.add("b"));
        assertTrue(bq.add("c"));
        try {
            bq.add("d");
        } catch (Exception e) {
            assertEquals("Queue full", e.getMessage());
        }
        assertFalse("offer 不会抛出异常，如果队列满了，返回 false", bq.offer("d"));

        assertEquals("a", bq.peek());


        assertEquals("a", bq.remove());
        assertEquals("b", bq.remove());
        assertEquals("c", bq.remove());
        try {
            bq.remove();
        } catch (Exception e) {
            assertTrue(e instanceof NoSuchElementException);
        }
        assertNull("poll 不会抛出异常，如果队列为空，返回 null", bq.poll());

        try {
            bq.element();
        } catch (Exception e) {
            assertTrue("如果队列为空，element 将抛出异常", e instanceof NoSuchElementException);
        }
        assertNull("如果队列为空，element 仅返回 null", bq.peek());

    }

}
