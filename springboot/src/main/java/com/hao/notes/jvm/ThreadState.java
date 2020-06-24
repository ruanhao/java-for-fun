package com.hao.notes.jvm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

import lombok.SneakyThrows;

public class ThreadState {

    @Test
    @SneakyThrows
    /*
     * VisualVM 标记为 sleeping
     *
     * "sleeping-thread" #11 prio=5 os_prio=31 tid=0x00007fa496177800 nid=0x5a03
     * waiting on condition [0x000070000ac88000] java.lang.Thread.State:
     * TIMED_WAITING (sleeping) at java.lang.Thread.sleep(Native Method) at
     * java.lang.Thread.sleep(Thread.java:340) at
     * java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386) at
     * com.hao.notes.jvm.ThreadState.lambda$0(ThreadState.java:16) at
     * com.hao.notes.jvm.ThreadState$$Lambda$1/665576141.run(Unknown Source) at
     * java.lang.Thread.run(Thread.java:748)
     *
     * Locked ownable synchronizers: - None
     */
    public void sleep() {
        Thread sleepingThread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }, "sleeping-thread");
        sleepingThread.start();
        TimeUnit.HOURS.sleep(1);
    }


    @Test
    @SneakyThrows
    /*
     * VisualVM 标记为 Wait
     *
     * "wait-thread" #11 prio=5 os_prio=31 tid=0x00007fe57d88c800 nid=0xa503 in
     * Object.wait() [0x000070000a5b6000] java.lang.Thread.State: WAITING (on object
     * monitor) at java.lang.Object.wait(Native Method) - waiting on
     * <0x000000076b432a68> (a java.lang.Object) at
     * java.lang.Object.wait(Object.java:502) at
     * com.hao.notes.jvm.ThreadState.lambda$1(ThreadState.java:48) - locked
     * <0x000000076b432a68> (a java.lang.Object) at
     * com.hao.notes.jvm.ThreadState$$Lambda$1/1058025095.run(Unknown Source) at
     * java.lang.Thread.run(Thread.java:748)
     *
     * Locked ownable synchronizers: - None
     */
    public void waitOnObject() {
        Object obj = new Object();
        Thread thread = new Thread(() -> {
            try {
                synchronized (obj) {
                    obj.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "wait-thread");
        thread.start();
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    @SneakyThrows
    /*
     * VisualVM 标记为 Wait
     *
     * "timed-waiting-thread" #11 prio=5 os_prio=31 tid=0x00007fc68b890000
     * nid=0x5903 in Object.wait() [0x000070000303a000] java.lang.Thread.State:
     * TIMED_WAITING (on object monitor) at java.lang.Object.wait(Native Method) -
     * waiting on <0x000000076b434478> (a java.lang.Object) at
     * com.hao.notes.jvm.ThreadState.lambda$2(ThreadState.java:79) - locked
     * <0x000000076b434478> (a java.lang.Object) at
     * com.hao.notes.jvm.ThreadState$$Lambda$1/1058025095.run(Unknown Source) at
     * java.lang.Thread.run(Thread.java:748)
     *
     * Locked ownable synchronizers: - None
     */
    public void timedWaitOnObject() {
        Object obj = new Object();
        Thread thread = new Thread(() -> {
            try {
                synchronized (obj) {
                    obj.wait(1000 * 3600);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "timed-waiting-thread");
        thread.start();
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    @SneakyThrows
    /*
     * VisualVM 标记为 Wait
     *
     * "main" #1 prio=5 os_prio=31 tid=0x00007f863b002800 nid=0x1803 in
     * Object.wait() [0x000070000b6b3000] java.lang.Thread.State: WAITING (on object
     * monitor) at java.lang.Object.wait(Native Method) - waiting on
     * <0x000000076b436190> (a java.lang.Thread) at
     * java.lang.Thread.join(Thread.java:1252) - locked <0x000000076b436190> (a
     * java.lang.Thread) at java.lang.Thread.join(Thread.java:1326) at
     * com.hao.notes.jvm.ThreadState.join(ThreadState.java
     */
    public void join() {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "join-thread");
        thread.start();
        thread.join();
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    @SneakyThrows
    /*
     * VisualVM 标记为 Wait
     *
     * "main" #1 prio=5 os_prio=31 tid=0x00007f9004800800 nid=0x2603 in
     * Object.wait() [0x000070000a143000] java.lang.Thread.State: TIMED_WAITING (on
     * object monitor) at java.lang.Object.wait(Native Method) - waiting on
     * <0x000000076b437588> (a java.lang.Thread) at
     * java.lang.Thread.join(Thread.java:1260) - locked <0x000000076b437588> (a
     * java.lang.Thread)
     */
    public void timedJoin() {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "timed-join-thread");
        thread.start();
        thread.join(1000 * 3600);
        TimeUnit.HOURS.sleep(1);
    }

    @Test
    @SneakyThrows
    /*
     * VisualVM 标记为 Park
     *
     * "park-thread" #13 prio=5 os_prio=31 tid=0x00007fdad39f3000 nid=0x5a03 waiting
     * on condition [0x0000700008487000] java.lang.Thread.State: TIMED_WAITING
     * (parking) at sun.misc.Unsafe.park(Native Method) at
     * java.util.concurrent.locks.LockSupport.parkNanos(LockSupport.java:338) at
     * com.hao.notes.jvm.ThreadState.lambda$5(ThreadState.java:160) at
     * com.hao.notes.jvm.ThreadState$$Lambda$1/665576141.run(Unknown Source) at
     * java.lang.Thread.run(Thread.java:748)
     *
     * Locked ownable synchronizers: - None
     */
    public void park() {
        Thread thread = new Thread(() -> {
            LockSupport.parkNanos(Long.MAX_VALUE);
            System.out.println("ok");
        }, "park-thread");
        thread.start();
        TimeUnit.HOURS.sleep(1);
    }

    @SneakyThrows
    @Test
    /*
     * VisualVM 标记为 Monitor
     *
     * "do-synchronized" #12 prio=5 os_prio=31 tid=0x00007fd1d09e6000 nid=0xa503
     * waiting for monitor entry [0x000070000b135000] java.lang.Thread.State:
     * BLOCKED (on object monitor) at
     * com.hao.notes.jvm.ThreadState.bar(ThreadState.java:196) - waiting to lock
     * <0x000000076b42e330> (a com.hao.notes.jvm.ThreadState) at
     * com.hao.notes.jvm.ThreadState$$Lambda$2/1876631416.run(Unknown Source) at
     * java.lang.Thread.run(Thread.java:748)
     *
     * Locked ownable synchronizers: - None
     */
    public void doSynchronized() {
        new Thread(this::foo).start();
        TimeUnit.SECONDS.sleep(1L);
        new Thread(this::bar, "do-synchronized").start();
        TimeUnit.HOURS.sleep(1L);
    }

    @SneakyThrows
    private synchronized void foo() {
        TimeUnit.HOURS.sleep(1L);
    }

    @SneakyThrows
    private synchronized void bar() {
        TimeUnit.HOURS.sleep(1L);
    }


}
