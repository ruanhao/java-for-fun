package com.hao.notes.jackson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class EnumSerializationTest {
    public enum Fruit {
        APPLE("apple"),
        BANANA("banana");

        String name;
        Fruit(String name) {
            this.name = name;
        }

        @JsonValue
        public String to() {
            return name;
        }

        @JsonCreator
        public Fruit from(String name) {
            for(Fruit item : values()){
                if(item.name().equals(name)){
                    return item;
                }
            }
            return null;
        }
    }

    private final static ObjectMapper OBJ_MAPPER = new ObjectMapper();

    @Test
    @SneakyThrows
    public void testSerialization() {
        String result = OBJ_MAPPER.writeValueAsString(Fruit.APPLE);
        System.out.println(result);
        assertEquals("\"apple\"", result);
    }

    @Test
    @SneakyThrows
    public void testDeserialization() {
        Fruit fruit = OBJ_MAPPER.readValue("\"banana\"", Fruit.class);
        System.out.println(fruit);
        assertEquals(Fruit.BANANA, fruit);
    }
}
