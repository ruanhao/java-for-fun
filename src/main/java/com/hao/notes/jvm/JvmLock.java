package com.hao.notes.jvm;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import lombok.SneakyThrows;

public class JvmLock {

    @Test
    public void printNoLockMemory() {
        MyLock lock = new MyLock();
//        com.hao.notes.jvm.MyLock object internals:
//            OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
//                 0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
//                 4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
//                 8     4        (object header)                           6b ef 00 f8 (01101011 11101111 00000000 11111000) (-134156437)
//                12     4    int MyLock.i                                  0
//           Instance size: 16 bytes
//           Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        System.out.println(Integer.toHexString(lock.hashCode())); // 458ad742
//        com.hao.notes.jvm.MyLock object internals:
//            OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
//                 0     4        (object header)                           01 42 d7 8a (00000001 01000010 11010111 10001010) (-1965604351)
//                 4     4        (object header)                           45 00 00 00 (01000101 00000000 00000000 00000000) (69)
//                 8     4        (object header)                           6b ef 00 f8 (01101011 11101111 00000000 11111000) (-134156437)
//                12     4    int MyLock.i                                  0
//           Instance size: 16 bytes
//           Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    @Test
    @SneakyThrows
    public void printBiasedLockMemory() {
        TimeUnit.SECONDS.sleep(5L);
        MyLock lock = new MyLock();
//        com.hao.notes.jvm.MyLock object internals:
//            OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
//                 0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
//                 4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
//                 8     4        (object header)                           6b ef 00 f8 (01101011 11101111 00000000 11111000) (-134156437)
//                12     4    int MyLock.i                                  0
//           Instance size: 16 bytes
//           Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    @Test
    @SneakyThrows
    public void printBiasdLockMemory2() {
        TimeUnit.SECONDS.sleep(5L);
        MyLock lock = new MyLock();
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        }).start();
        TimeUnit.SECONDS.sleep(3L);
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    @Test
    @SneakyThrows
    public void printLightWeightLockMemory() {
        Thread.sleep(5000);
        MyLock l = new MyLock();

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                synchronized (l){
                    System.out.println("thread1 locking");
                    System.out.println(ClassLayout.parseInstance(l).toPrintable());
                }
                try {
                    // thread1 退出同步代码块，但没有结束
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                synchronized (l){
                    System.out.println("thread2 locking");
                    System.out.println(ClassLayout.parseInstance(l).toPrintable());
                }
            }
        };

        thread1.start();
        // 让thread1执行完同步代码块中方法。
        Thread.sleep(4000);
        thread2.start();
        Thread.sleep(3000);
        System.out.println("All finished");
        System.out.println(ClassLayout.parseInstance(l).toPrintable());
    }

    @Test
    @SneakyThrows
    public void printHeavyLockMemory() {
        Thread.sleep(5000);
        MyLock l = new MyLock();
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                synchronized (l){
                    System.out.println("thread1 locking");
                    System.out.println(ClassLayout.parseInstance(l).toPrintable());
                    try {
                        // 让线程晚点儿终止，造成锁的竞争
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                synchronized (l){
                    System.out.println("thread2 locking");
                    System.out.println(ClassLayout.parseInstance(l).toPrintable());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread1.start();
        thread2.start();
        Thread.sleep(3000);
    }




}

class MyLock {
    int i;
}

