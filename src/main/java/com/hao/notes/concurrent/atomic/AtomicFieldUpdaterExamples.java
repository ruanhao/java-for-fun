package com.hao.notes.concurrent.atomic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import org.junit.Test;

import lombok.Builder;
import lombok.Data;

public class AtomicFieldUpdaterExamples {

    @Test
    public void testCasOperation() {
        AtomicIntegerFieldUpdater<MyObject> valueUpdater =
                AtomicIntegerFieldUpdater.newUpdater(MyObject.class, "value");
        int originValue = new Random().nextInt();
        MyObject myObj = MyObject.builder().value(originValue).build();
        int newValue = new Random().nextInt();
        assertTrue(valueUpdater.compareAndSet(myObj, originValue, newValue));
        assertEquals(newValue, myObj.value);
        int anotherNewValue = new Random().nextInt();
        assertFalse(valueUpdater.compareAndSet(myObj, originValue, anotherNewValue));
        assertNotEquals(anotherNewValue, myObj.value);
    }
}

@Data
@Builder
class MyObject {

    volatile int value;

}
