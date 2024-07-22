package me.bremado.core.cache;

import me.bremado.core.cache.interfaces.ICache;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Cache<T> implements ICache<T> {

    private List<T> cache = new ArrayList<>();

    @Override
    public void save(T object) {
        cache.add(object);
    }

    @Override
    public void delete(Predicate<T> predicate) {
        cache.removeIf(predicate);
    }

    @Override
    public void update(T object) {
        cache.removeIf(t -> t.equals(object));
        cache.add(object);
    }


    @Override
    public T find(Predicate<T> predicate) {
        return cache.stream().filter(predicate).findFirst().orElse(null);
    }

    @Override
    public List<T> findAll() {
        return cache;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }
}
