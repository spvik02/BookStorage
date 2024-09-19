package edu.spv.books.entity;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SimpleCache<K, V> implements Cache<K, V> {

    private final Map<K, V> cache;

    public SimpleCache() {
        this.cache = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.get(key));
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
