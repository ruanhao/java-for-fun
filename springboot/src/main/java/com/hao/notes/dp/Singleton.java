package com.hao.notes.dp;

public class Singleton {


}


/*
 * 恶汉式，一上来就实例化对象
 */
class Hungry {

    private Hungry() {}

    private static final Hungry INSTANCE = new Hungry();

    public static Hungry getInstance() {
        return INSTANCE;
    }
}

/*
 * 使用 DCL 创建懒汉式单例
 */
class Lazy {

    private Lazy() {
        synchronized (Lazy.class) {
            if (INSTANCE != null) {
                throw new RuntimeException("不要试图使用反射破坏单例"); // 这种保护只是象征性的，反射无法防止，除非使用枚举创建单例
            }
        }
    }

    private static volatile Lazy INSTANCE = null;

    public static Lazy getInstance() {
        if (INSTANCE == null) {
            synchronized (Lazy.class) {
                if (INSTANCE == null) { // 这里判断 null 的原因在于如果首个拿锁者的创建对象期间，有其他线程同步调用 getInstance() ，
                                        // 那么它们也会试图拿锁然后阻塞，如果这时不判断的话，则会造成第二次初始化。

                    // new 是个多步操作：
                    // 1. 在堆中分配对象内存
                    // 2. 填充对象必要信息 + 具体数据初始化 + 末位填充
                    // 3. 将引用指向这个对象的堆内地址
                    // 因此需要使用 volatile 关键字防止指令重排
                    INSTANCE = new Lazy();
                }

            }
        }
        return INSTANCE;
    }

}

// 使用 Holder 模式实现懒汉单例
class AnotherLazy {

    private AnotherLazy() {

    }

    public static AnotherLazy getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final AnotherLazy INSTANCE = new AnotherLazy();
    }
}