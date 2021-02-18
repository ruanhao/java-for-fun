package com.hao.ds.solution.recursion;

import java.util.stream.Stream;

@FunctionalInterface
public interface TailRecursion<T> {
    /**
     * @return next stack frame
     */
    TailRecursion<T> apply();

    default boolean isFinished(){
        return false;
    }

    default T getResult()  {
        throw new RecursionNotFinishedYetException();
    }

    default T invoke() {
        return Stream.iterate(this, TailRecursion::apply)
                .filter(TailRecursion::isFinished)
                .findFirst()
                .get()
                .getResult();
    }
}
