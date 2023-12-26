package com.example.backend.utils;

@FunctionalInterface
public interface TriThrowingTriConsumer<E1 extends Exception, E2 extends Exception, E3 extends Exception, T, U, V> {
    void accept(T t, U u, V v) throws E1, E2, E3;
}
