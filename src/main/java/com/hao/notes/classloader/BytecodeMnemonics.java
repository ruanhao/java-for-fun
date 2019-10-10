package com.hao.notes.classloader;

/*
 * 字节码助记符
 *
 * ldc: 将 int, float 或是 String 类型的常量值从常量池中推送至栈顶
 * bipush: 将单字节 (-128~127) 常量推送至栈顶
 * sipush: 将一个短整形常量 (-32768~32767) 推送至栈顶
 * iconst_<v>: 将 int 型数值 <v> 推送至栈顶，v 的范围是 0~5
 *
 */

/*javap -c com.hao.notes.classloader.BytecodeMnemonics
Compiled from "BytecodeMnemonics.java"
public class com.hao.notes.classloader.BytecodeMnemonics {
  public com.hao.notes.classloader.BytecodeMnemonics();
    Code:
       0: aload_0
       1: invokespecial #8                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #16                 // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #22                 // String hello
       5: invokevirtual #24                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: getstatic     #16                 // Field java/lang/System.out:Ljava/io/PrintStream;
      11: iconst_2
      12: invokevirtual #30                 // Method java/io/PrintStream.println:(I)V
      15: getstatic     #16                 // Field java/lang/System.out:Ljava/io/PrintStream;
      18: bipush        6
      20: invokevirtual #30                 // Method java/io/PrintStream.println:(I)V
      23: getstatic     #16                 // Field java/lang/System.out:Ljava/io/PrintStream;
      26: sipush        300
      29: invokevirtual #30                 // Method java/io/PrintStream.println:(I)V
      32: return
}*/
public class BytecodeMnemonics {
    public static void main(String[] args) {
        System.out.println(MnemonicsTest.str);
        System.out.println(MnemonicsTest.s);
        System.out.println(MnemonicsTest.b);
        System.out.println(MnemonicsTest.i);
    }
}

class MnemonicsTest {
    public final static String str = "hello";
    public final static short s = 2;
    public final static byte b = 6;
    public final static int i = 300;
}
