package com.hao.notes.jvm;


import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Type;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.ClassEmitter;
import org.springframework.cglib.core.Constants;
import org.springframework.cglib.core.EmitUtils;
import org.springframework.cglib.core.NamingPolicy;
import org.springframework.cglib.core.Predicate;

public class JvmMetaspace {

    @Test
    /*
     * -XX:MaxMetaspaceSize=50M -XX:+PrintGCDetails
     * 可以观察到：java.lang.OutOfMemoryError: Metaspace
     */
    public void testGenerateClassDynamically() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println("pid: " + pid);
        int i = 0;
        try {
            for (i = 0; i < Integer.MAX_VALUE; i++) {
                @SuppressWarnings("unused")
                CglibBean bean = new CglibBean("com.hao.notes.jvm.meta" + i, new HashMap<>());
            }
        } catch (Exception e) {
            System.out.println("total create count:" + i);
            throw e;
        }
    }
}


class CglibBean {

    public Object object = null;

    public BeanMap beanMap = null;

    public CglibBean() {
        super();
    }


    public CglibBean(Map<String, Class<?>> propertyMap) {
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }

    public CglibBean(String className, Map<String, Class<?>> propertyMap) {
        this.object = generateBean(className, propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }

    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    public Object getValue(String property) {
        return beanMap.get(property);
    }

    public Object getObject() {
        return this.object;
    }

    private Object generateBean(Map<String, Class<?>> propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set<String> keySet = propertyMap.keySet();
        for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
            String key = i.next();
            generator.addProperty(key, propertyMap.get(key));
        }
        return generator.create();
    }

    private Object generateBean(final String className, Map<String, Class<?>> propertyMap) {
        BeanGeneratorObj generator = new BeanGeneratorObj();
        generator.setUseCache(false);
        generator.setNamingPolicy(new NamingPolicy() {
            @Override
            public String getClassName(String prefix, String source, Object key, Predicate names) {
                return className;
            }
        });

        Set<String> keySet = propertyMap.keySet();
        for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
            String key = i.next();
            generator.addProperty(key, propertyMap.get(key));
        }
        return generator.create();
    }
}

class BeanGeneratorObj extends BeanGenerator{
    public BeanGeneratorObj() {
        super();
    }

    private Map<String, Type> props = new HashMap<>();

    @Override
    public void addProperty(String name, @SuppressWarnings("rawtypes") Class type) {
        super.addProperty(name, type);
        if (props.containsKey(name)) {
            throw new IllegalArgumentException("Duplicate property name \"" + name + "\"");
        }
        props.put(name, Type.getType(type));
    }

    @Override
    public void generateClass(ClassVisitor v) throws Exception {
        int size = props.size();
        String[] names = props.keySet().toArray(new String[size]);
        Type[] types = new Type[size];
        for (int i = 0; i < size; i++) {
            types[i] = props.get(names[i]);
        }
        ClassEmitter ce = new ClassEmitter(v);
        ce.begin_class(Constants.V1_2, Constants.ACC_PUBLIC, getClassName(),
                getDefaultClassLoader() != null ? Type.getType(getDefaultClassLoader().getClass())
                        : Constants.TYPE_OBJECT, null, null);
        EmitUtils.null_constructor(ce);
        add_properties(ce, names, types);
        ce.end_class();
    }

    private void add_properties(ClassEmitter ce, String[] names, Type[] types) {
        for (int i = 0; i < names.length; i++) {
            String fieldName = names[i];
            ce.declare_field(Constants.ACC_PRIVATE, fieldName, types[i], null);
            EmitUtils.add_property(ce, names[i], types[i], fieldName);
        }
    }
}