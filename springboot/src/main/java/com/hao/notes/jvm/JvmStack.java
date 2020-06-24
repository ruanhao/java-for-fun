package com.hao.notes.jvm;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JvmStack {

    private static int count = 0;

    public static void recursion(){
        count++;
        recursion();
    }

    @SuppressWarnings("unused")
    public static void recursion(long a,long b,long c){
        long e = 1, f = 2, g = 3, h = 4, i = 5, k = 6, q = 7, x = 8, y = 9, z = 10;
        count++;
        recursion(a,b,c);
    }

    @Test
    /*
     * 在相同栈容量下，局部变量少的函数可以支持更深层次的函数调用
     */
    public void testStackDeepWithLocalVariableTable() {
        count = 0;
        int withoutLVT = 0;
        int withLVT = 0;

        try{
            recursion();
        }catch(Throwable e){
            withoutLVT = count;
        }

        count = 0;
        try{
            recursion(1, 2, 3);
        }catch(Throwable e){
            withLVT = count;
        }

        System.out.println(String.format("withLVT: %d, withoutLVT: %d", withLVT, withoutLVT));
        assertTrue(withoutLVT > withLVT);

    }

    @Test
    /*
     * 分别使用 JVM 参数 -Xss256K 和 -Xss512K 运行该测试，观察结果
     * 256K 对应深度为 2279
     * 512K 对应深度为 8577
     */
    public void testStackDeep() {
        try{
            recursion();
        }catch(Throwable e){
            System.out.println("deep of calling = " + count);
            e.printStackTrace();
        }
    }
}
