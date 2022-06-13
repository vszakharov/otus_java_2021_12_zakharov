package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private static final String PUT = "put";
    private static final String REMOVE = "remove";
    private static final String GET = "get";

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        if (key != null && value != null) {
            cache.put(key, value);
            notify(key, value, PUT);
        }
    }

    @Override
    public void remove(K key) {
        if (cache.containsKey(key)) {
            var value = cache.get(key);
            cache.remove(key);
            notify(key, value, REMOVE);
        }
    }

    @Override
    public V get(K key) {
        var value = cache.get(key);
        notify(key, value, GET);

        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notify(K key, V value, String action) {
        listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
