package com.hao.notes.jvm;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

/*
 * 软应用和弱引用适合保存可有可无的缓存数据，当系统内存不足时，这些缓存数据会被回收，不会导致内存溢出；
 * 当内存富裕时，缓存数据可以存在较长的时间。
 *
 * 虚引用是所有引用中最弱的，持有虚引用的对象，和没有引用几乎是一样的。
 * 当试图通过虚引用的 get() 方法获得强引用时，总会失败。
 * 虚引用必须和引用队列一起使用，它的作用在于跟踪垃圾回收过程，可以将一些资源释放操作放在虚引用中执行。
 *
 */
public class ReferenceExamples {

    private static ReferenceQueue<User> softRefQueue = new ReferenceQueue<>();
    private static ReferenceQueue<User> phantomRefQueue = new ReferenceQueue<>();


    @Test
    /*
     * -Xmx10m -XX:+PrintGC
     * GC 未必会回收软引用的对象，但是当内存资源紧张时，软引用对象会被回收，
     * 所以软引用不会引起内存溢出。
     */
    public void recycleObjectSoftReferenced() {
        User user = new User(1, "user1");
        SoftReference<User> userSoftRef = new SoftReference<User>(user);
        user = null;

        assertNotNull("可以从软引用中获取对象", userSoftRef.get());
        System.gc();
        assertNotNull("虽然 gc ，但是内存资源并不紧张，仍然可以从软引用中获取对象", userSoftRef.get());

        @SuppressWarnings("unused")
        byte[] bytes = new byte[6121872]; // 可以尝试调整内存大小
        System.out.println("分配较大内存...");
        System.gc();
        assertNull("内存资源紧张，软引用对象被回收", userSoftRef.get());
    }

    @Test
    @SneakyThrows
    /*
     * -Xmx10m -XX:+PrintGC
     */
    public void testSoftReferenceQueue() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Reference<? extends User> refToRemoved = softRefQueue.remove();
                    UserSoftReference userRefToRemoved = (UserSoftReference) refToRemoved;
                    System.out.println(userRefToRemoved.uid + " -> removed"); // 可以在控制台观察到该输出
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        User user = new User(1, "soft ref queue test");
        UserSoftReference userSoftRef = new UserSoftReference(user, softRefQueue);
        System.gc();
        assertNotNull(userSoftRef.get());
        user = null;
        @SuppressWarnings("unused")
        byte[] bytes = new byte[6121872]; // 可以尝试调整内存大小
        System.gc();
    }


    @Test
    /*
     * 弱引用是一种比软引用更弱的引用类型。
     * GC 时，只要发现有弱引用，不管堆空间情况如何，一律回收。
     */
    public void testWeakReference() {
        User user = new User(2, "weak ref");
        WeakReference<User> weakUserRef = new WeakReference<User>(user);
        user = null;
        assertNotNull(weakUserRef.get());
        System.gc();
        assertNull("不管当前内存是否足够，都会回收", weakUserRef.get());
    }

    @Test
    public void testPhantomReference() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Reference<? extends User> refToRemoved = phantomRefQueue.remove();
                    UserPhantomReference userRefToRemoved = (UserPhantomReference) refToRemoved;
                    System.out.println(userRefToRemoved.uid + " -> removed"); // 可以在控制台观察到该输出
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        User user = new User(3, "phantom");
        UserPhantomReference phantomRef = new UserPhantomReference(user, phantomRefQueue);
        assertNull("试图通过虚引用的 get() 方法获得强引用时，总返回 null", phantomRef.get());
        user = null;
        System.gc();
    }

}

@AllArgsConstructor
@ToString
class User {
    int id;
    String name;
}

/*
 * 定义一个类继承 SoftReference ，可以用某个属性（如 uid ）与对象进行关联，
 * 这样，当对象被回收时，JVM 通过引用即可找到该 SoftReference 对象，进而查询关联关系。
 * 直接使用 new SoftReference(referent, queue) 也可以，但是无法获取对象的信息。
 */
class UserSoftReference extends SoftReference<User> {

    int uid;

    public UserSoftReference(User referent, ReferenceQueue<? super User> queue) {
        super(referent, queue);
        this.uid = referent.id;
    }

}

class UserPhantomReference extends PhantomReference<User> {

    public UserPhantomReference(User referent, ReferenceQueue<? super User> q) {
        super(referent, q);
        uid = referent.id;
    }

    int uid;


}
