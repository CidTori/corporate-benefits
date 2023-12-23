package com.example.backend.utils;

@FunctionalInterface
public interface ThrowingTriConsumer<E extends Exception, T, U, V> {
    void accept(T t, U u, V v) throws E;
}
