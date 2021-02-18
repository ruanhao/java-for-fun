package com.hao.ds.solution.recursion;

import java.math.BigInteger;
import org.junit.Test;

import static org.junit.Assert.*;

public class TailRecursionTest {

    private static long NUMBER = 10_0000;

    private BigInteger factorial(final long number) {
        return factorial(number, BigInteger.ONE).invoke();
    }

    private TailRecursion<BigInteger> factorial(final long number, final BigInteger result) {
        // System.out.println("current result: " + result);
        if (number == 1)
            return TailInvoke.done(result);
        else
            return () -> factorial(number - 1, result.multiply(BigInteger.valueOf(number)));
    }

    private BigInteger factorial2(long number) {
        System.out.println("Handling " + number);
        if (number == 1) return BigInteger.ONE;
        return factorial2(number - 1).multiply(BigInteger.valueOf(number));
    }

    @Test(expected = StackOverflowError.class)
    public void testWithoutTailRecursion() {
        BigInteger result = factorial2(NUMBER);
        System.out.println("Result: " + result);
    }

    @Test
    public void testTailRecursion() {
        BigInteger finalResult = factorial(NUMBER);
        System.out.println("Final result: " + finalResult);
    }

}