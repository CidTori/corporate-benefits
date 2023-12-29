package com.example.backend.utils;

@FunctionalInterface
public interface TriThrowingTriFunction<E1 extends Exception, E2 extends Exception, E3 extends Exception, R, T, U, V> {
    R apply(T t, U u, V v) throws E1, E2, E3;
}
