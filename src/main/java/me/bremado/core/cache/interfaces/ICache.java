package me.bremado.core.cache.interfaces;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public interface ICache<T> {

    void save(T object);
    void delete(Predicate<T> predicate);

    void update(T object);

    T find(Predicate<T> predicate);
    List<T> findAll();

    void clear();
    int size();
}
