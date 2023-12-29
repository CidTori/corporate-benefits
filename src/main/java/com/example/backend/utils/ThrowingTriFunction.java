package com.example.backend.utils;

@FunctionalInterface
public interface ThrowingTriFunction<E extends Exception, R, T, U, V> {
    R apply(T t, U u, V v) throws E;
}
