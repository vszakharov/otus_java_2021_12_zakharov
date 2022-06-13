package ru.otus.cachehw;


import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);
    private static final String PUT = "put";
    private static final String REMOVE = "remove";
    private static final String GET = "get";

    private final Map<K, V> cache = new WeakHashMap<>();
    private final ReferenceQueue<HwListener<K, V>> referenceQueue = new ReferenceQueue<>();
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

    public MyCache() {
        start();
    }

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
            listeners.add(new WeakReference<>(listener, referenceQueue));
        }

    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
//        listeners.remove(listener);
    }

    private void notify(K key, V value, String action) {
        listeners.forEach(listener -> listener.get().notify(key, value, action));
    }

    private void start() {
        new Thread(
            () -> {
                while (true) {
                    try {
                        Reference<? extends HwListener<K, V>> removed = referenceQueue.remove();
                        listeners.remove(removed);
                        logger.info("Object cleaned:{}", removed);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        ).start();
    }
}
