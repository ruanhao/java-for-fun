package com.hao.notes.jvm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import lombok.SneakyThrows;

public class TypeReference {

    // TypeReference 的存在是因为 java 中子类可以获取到父类泛型的真实类型
    @Test
    public void testGettingSuperClassGenericInfo() {
        MyMap myMap = new MyMap();

        // 子类
        assertEquals(HashMap.class, myMap.getClass().getSuperclass());
        Type type = myMap.getClass().getGenericSuperclass();
        assertTrue(type instanceof ParameterizedType);
        assertEquals("Can obtain generic class of String",
                String.class, ((ParameterizedType) type).getActualTypeArguments()[0]);
        assertEquals("Can obtain generic class of Integer",
                Integer.class, ((ParameterizedType) type).getActualTypeArguments()[1]);


        // 直接类
        HashMap<String,Integer> newMyMap = new HashMap<>();
        assertEquals(AbstractMap.class, newMyMap.getClass().getSuperclass());
        Type newClassType = newMyMap.getClass().getGenericSuperclass();
        assertTrue(newClassType instanceof ParameterizedType);
        assertEquals("K", ((ParameterizedType) newClassType).getActualTypeArguments()[0].getTypeName());
        assertEquals("V", ((ParameterizedType) newClassType).getActualTypeArguments()[1].getTypeName());


        // 匿名内部类
        HashMap<String,Integer> subIntMap =
                new HashMap<String,Integer>(){
                    private static final long serialVersionUID = 1L;
                };

        assertEquals(HashMap.class, subIntMap.getClass().getSuperclass());
        Type subClassType = subIntMap.getClass().getGenericSuperclass();
        assertTrue(subClassType instanceof ParameterizedType);
        assertEquals("获取到了真实的类型，就可以实现对泛型的反序列化",
                String.class, ((ParameterizedType) subClassType).getActualTypeArguments()[0]);
        assertEquals(Integer.class, ((ParameterizedType) subClassType).getActualTypeArguments()[1]);

    }

    // java 虽然运行时会有类型擦除，但是会保留 Field 的泛型信息，可以通过Field.getGenericType() 取字段的泛型
    @Test
    @SneakyThrows
    public void testGettingFieldGenericInfo() {
        MyMap myMap = new MyMap();

        Field map = myMap.getClass().getField("map");
        Field list = myMap.getClass().getField("list");

        assertEquals(Map.class, map.getType());
        assertEquals("java.util.Map<java.lang.String, java.lang.Integer>", map.getGenericType().getTypeName());
        assertEquals(String.class, (((ParameterizedType) map.getGenericType()).getActualTypeArguments()[0]));
        assertEquals(Integer.class, (((ParameterizedType) map.getGenericType()).getActualTypeArguments()[1]));

        assertEquals(List.class, list.getType());
        assertEquals("java.util.List<java.lang.Long>", list.getGenericType().getTypeName());
        assertEquals(Long.class, (((ParameterizedType) list.getGenericType()).getActualTypeArguments()[0]));

    }


}

class MyMap extends HashMap<String, Integer> {

    public Map<String,Integer> map = new HashMap<>();
    public List<Long> list = new ArrayList<>();

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
