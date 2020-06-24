package com.hao.notes.spring;

import com.hao.notes.spring.components.Person;
import com.hao.notes.spring.components.Student;
import static org.junit.Assert.*;

import com.hao.notes.spring.components.circledep.X;
import com.hao.notes.spring.config.*;
import java.util.Optional;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Map;

public class SpringLearning {


    @Test
    public void componentScan() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(MyConfig.class);
        Object person = appContext.getBean("person");
        Student student = appContext.getBean(Student.class);
        assert person != null : "通过 @Bean 的方式注入";
        assert student != null : "通过 @ComponentScan 的方式注入";
        System.err.println("appContext.getBeanDefinitionNames(): " + Arrays.asList(appContext.getBeanDefinitionNames()));
        assertFalse("基于 FilterType.ANNOTATION 被排除掉了", appContext.containsBean("badGuy"));
    }

    @Test
    public void scope() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ScopeConfig.class);
        Object person1 = appContext.getBean("person");
        Object person2 = appContext.getBean("person");

        Object student1 = appContext.getBean("student");
        Object student2 = appContext.getBean("student");

        assertTrue("单例", person1 == person2);
        assertTrue("原型", student1 != student2);
    }


    @Test
    public void conditional() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ConditionalConfig.class);
        Map<String, Person> personBeans = appContext.getBeansOfType(Person.class);
        assertTrue(personBeans.keySet().contains("jobs"));
        assertFalse(personBeans.keySet().contains("bill"));
    }

    @Test
    public void factoryBean() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
        Object person1 = appContext.getBean("personFactory"); // 取的是 factory ，但是返回的却是 Person
        System.err.println("personFactory: " + person1);
        assertTrue("Factory bean 的类型是其返回的对象的类型",
                person1.getClass() == Person.class);
        Object person2 = appContext.getBean("personFactory");
        System.err.println("person2: " + person2);
        assertNotSame("多实例", person1, person2);

        Object factory = appContext.getBean("&personFactory"); // 获取 factory 对象需要加前缀
        assertEquals(factory.getClass().getSimpleName(), "PersonFactory");
    }

    @Test
    public void lifeCycle() {
        // 注意看输出
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(LifecycleConfig.class);
        appContext.close();
    }

    @Test
    public void profile() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.getEnvironment().setActiveProfiles("dev", "prod");
        appContext.register(ProfileConfig.class);
        appContext.refresh();

        Map<String, Person> people = appContext.getBeansOfType(Person.class);
        assertTrue(people.containsKey("productor"));
        assertTrue(people.containsKey("developer"));
        assertFalse(people.containsKey("tester"));

    }

    @Test
    public void objectProvider() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ObjectProviderConfig.class);
        // user bean 在这一步已经初始化好了
        System.err.println("appContext is ready");
        ObjectProviderConfig objectProviderConfig = appContext.getBean(ObjectProviderConfig.class);
        System.err.println("objectProviderConfig got");

        System.err.println("objectProviderConfig.getUserObjectProvider().getObject(): " + objectProviderConfig.getUserObjectProvider().getObject());
        // 如果 User 使用了 @Lazy ，则 user bean 在 getObject() 的时候被初始化

        // =============
        // Optional 方式无视 @Lazy
        Optional<com.hao.notes.spring.components.object.provider.Person> personOpt = objectProviderConfig.getPersonOpt();
        System.err.println("personOpt: " + personOpt);
        System.err.println("personOpt.get(): " + personOpt.get());
    }

    @Test
    public void circleDep() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(LearnCircleDepConfig.class);
        X x = appContext.getBean(X.class);
        System.err.println("x: " + x.getClass());
        x.div(2, 1);
        System.err.println("=============");
        try {
            x.div(2, 0);
        } catch (Exception e) {

        }



    }

}
