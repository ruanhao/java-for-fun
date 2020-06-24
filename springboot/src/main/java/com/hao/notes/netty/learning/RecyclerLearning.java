package com.hao.notes.netty.learning;

import io.netty.util.Recycler;

public class RecyclerLearning {

    private static Recycler<User> RECYCLER = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    private static void test1() {
        User user1 = RECYCLER.get();

        User user2 = RECYCLER.get(); // 0
        User user3 = RECYCLER.get(); // 1
        User user4 = RECYCLER.get(); // 2
        User user5 = RECYCLER.get(); // 3
        User user6 = RECYCLER.get(); // 4
        User user7 = RECYCLER.get(); // 5
        User user8 = RECYCLER.get(); // 6
        User user9 = RECYCLER.get(); // 7
        User user10 = RECYCLER.get(); // 8
        user1.recycle();
        user2.recycle();
        user3.recycle();
        user4.recycle();
        user5.recycle();
        user6.recycle();
        user7.recycle();
        user8.recycle();
        user9.recycle();
        user10.recycle();

        System.err.println("user1: " + user1);
        System.err.println("user2: " + user2);

        assert RECYCLER.get() == user10;
        assert RECYCLER.get() == user1;
    }

    private static void test2() {
        User user2 = RECYCLER.get();
        user2.recycle();
        user2 = RECYCLER.get();
        user2.recycle();
        assert  RECYCLER.get() != user2;
    }

    public static void main(String[] args) {
       // test1();
        test2();



    }
}

class User {

    private Recycler.Handle handle;

    public User(Recycler.Handle handle) {
        System.err.println(String.format("[Creating USER] %s => %s", handle, this));
        this.handle = handle;
    }

    public void recycle() {
        handle.recycle(this);
    }
}
