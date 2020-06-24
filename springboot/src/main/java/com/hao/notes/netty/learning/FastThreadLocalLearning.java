package com.hao.notes.netty.learning;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

public class FastThreadLocalLearning {

    public static FastThreadLocal<String> threadLocal = new FastThreadLocal<String>() {
        @Override
        protected String initialValue() throws Exception {
            return "initValue";
        }
    };


    public static void main(String[] args) {
        String value = threadLocal.get();
        System.err.println(value);

        new FastThreadLocalThread(() -> {
            String value1 = threadLocal.get();
            System.err.println(value1);
        }).start();






    }

}
