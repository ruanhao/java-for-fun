package com.hao.notes.jvm;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BtraceExample {

    private static EventLoopGroup eventLoopGroup = new DefaultEventLoopGroup(4, new DefaultThreadFactory("btrace-test"));

    /*
     * 监控函数运行时间
     */
    /*
    @OnMethod(clazz = "com.hao.notes.jvm.BtraceExample", method = "timeConsumingTask", location = @Location(Kind.RETURN))
    public static void traceMethodRunningTime(@Duration long duration) {
        println("=== " + timestamp("yyyy-MM-dd HH:mm:ss") + " ===");
        println(strcat("duration(ms): ", str(duration / 1000000)));
    }
    */

    // 跟踪函数返回值
    /*
    @OnMethod(clazz="com.hao.notes.jvm.BtraceExample", method="newStudent", location=@Location(Kind.RETURN))
    public static void traceMetherReturnValue(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Return String result) {
        println("=== " + timestamp("yyyy-MM-dd HH:mm:ss") + "/" + probeClass + "/" + probeMethod + " ===");
        println(result);
    }
    */

    // 使用正则表达式进行跟踪
    // @OnMethod(clazz="/com\\.hao\\.notes\\.jvm\\..*trace.*/", method="/.*/")
    // public static void traceByRegex(@ProbeClassName String pcn, @ProbeMethodName String pmn) {
    //     println("execute on " + pcn + "/" + pmn);
    // }

    // 根据行号跟踪
    /*
    @OnMethod(clazz="com.hao.notes.jvm.BtraceExample", location=@Location(value=Kind.LINE, line=-1)) // -1 表示所有行
    public static void traceByLineNum(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, int line) {
        println("=== " + timestamp("yyyy-MM-dd HH:mm:ss") + " ===");
        println(probeClass + "."  + probeMethod);
        println("#" + line);
    }
    */

    // 跟踪方法参数
    /*
    @OnMethod(clazz="com.hao.notes.jvm.BtraceExample", method="newStudent")
    public static void traceMethodArgs(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, AnyType[] args) {
        println("=== " + timestamp("yyyy-MM-dd HH:mm:ss") + " ===");
        printArray(args);
        println(Threads.name(Threads.currentThread())); // 打印线程名字
    }
     */

    // 周期性检查死锁，如果存在死锁，则打印
    /*
    @OnTimer(4000)
    public static void checkDeadlock() {
        println("=== " + timestamp("yyyy-MM-dd HH:mm:ss") + " ===");
        deadlocks();
    }
    */

    // 检查 this 对象属性
    /*
    @OnMethod(clazz="com.hao.notes.jvm.Person", method="sayHello")
    public static void onTriggered(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod, @Self Object me) {
        println("=== " + timestamp("yyyy-MM-dd HH:mm:ss") + " ===");
        printFields(me);

    }
    */

// 监控函数内调用其他函数的运行时间
//    @OnMethod(clazz="com.hao.notes.jvm.Person", method="sayHello", location=@Location(value=Kind.CALL, clazz="/.*/", method="/.*/", where=Where.AFTER))
//    public static void onTriggered(@Self Object self,
//            @TargetInstance Object instance, @TargetMethodOrField(fqn=true) String method,
//            @Duration long duration) {
//        println("=== " + timestamp("yyyy-MM-dd HH:mm:ss") + " ===");
//        println(str(instance)); // 如果是静态方法，instance == null
//        println(method + ": " + duration/1000000 + " ms");
//   }


    public static void main(String[] args) {
        eventLoopGroup.submit(() -> {
            while (true) {
                timeConsumingTask();
            }
        });
        eventLoopGroup.submit(() -> {
           while (true) {
               newStudent();
               newStudent("student", new Random().nextInt());
               new Person(new Random().nextInt()).sayHello();
               TimeUnit.SECONDS.sleep(1);
           }
        });
    }



    @SneakyThrows
    public static void timeConsumingTask() {
        log.info("Running time consuming task");
        TimeUnit.SECONDS.sleep(3L);
    }

    private static String newStudent() {
        log.info("Generating new student...");
        int age = new Random().nextInt(100);
        return new Student("student-" + age, age).toString();
    }

    private static String newStudent(String name, int age) {
        log.info("Generating new student manualy ...");
        return new Student(name, age).toString();
    }

}

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
class Student {
    String name;
    int age;
}

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
class Person {
    public int age;

    public void sayHello() {
        System.out.println("hi");
        BtraceExample.timeConsumingTask();
    }
}