package com.formedix.loader;

public interface Parser<T> {
    void parse(String line);
    T getResult();
}
