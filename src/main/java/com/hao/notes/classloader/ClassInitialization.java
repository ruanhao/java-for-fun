package com.hao.notes.classloader;

/*
 * 类的加载与初始化
 * -XX:+TraceClassLoading => 打印类的加载信息
 */
public class ClassInitialization {
    public static void main(String[] args) {
        /*
         * 程序输出:
         * parent static block
         * parent
         *
         * 这是因为并没有对 Child 主动使用，而是主动使用了 Parent。
         * 定义静态变量的类才会被初始化。
         */
        System.out.println(Child.pName);

        /*
         * 程序输出:
         * final static field
         *
         * 静态常量在编译阶段就会存入调用该常量的方法所在的类的常量池中，
         * 本质上，调用类并没有直接引用到定义常量的类，因此并不会触发定义常量的类的初始化
         * (field 存放到了 ClassInitialization 类的常量池中)
         * 甚至在编译后，可以将 FinalStaticFieldTest 的字节码文件删除，也不会影响运行结果
         *
         * 运行 `javap -c com.hao.notes.classloader.ClassInitialization`，输出:
         *
         * Compiled from "ClassInitialization.java"
         * public class com.hao.notes.classloader.ClassInitialization {
         *   public com.hao.notes.classloader.ClassInitialization();
         *     Code:
         *        0: aload_0
         *        1: invokespecial #8                  // Method java/lang/Object."<init>":()V
         *        4: return
         *
         *   public static void main(java.lang.String[]);
         *     Code:
         *        0: getstatic     #16                 // Field java/lang/System.out:Ljava/io/PrintStream;
         *        3: getstatic     #22                 // Field com/hao/notes/classloader/Child.pName:Ljava/lang/String;
         *        6: invokevirtual #28                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         *        9: getstatic     #16                 // Field java/lang/System.out:Ljava/io/PrintStream;
         *       12: ldc           #34                 // String final static string field # 可以看出字符串已经出现在字节码文件中，和定义常量的类已无关联
         *                                             # ldc 表示将 int, float 或是 String 类型的常量值从常量池中推送至栈顶
         *       14: invokevirtual #28                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         *       17: return
         * }
         *
         */
        System.out.println(FinalStaticFieldTest.stringField);
    }
}

class Parent {
    public static String pName = "parent";

    static {
        System.out.println("parent static block");
    }
}

class Child extends Parent {
    public static String cName = "child";

    static {
        System.out.println("child static block");
    }
}

class FinalStaticFieldTest {
    public static final String stringField = "final static string field";

    static {
        System.out.println("static block");
    }
}
