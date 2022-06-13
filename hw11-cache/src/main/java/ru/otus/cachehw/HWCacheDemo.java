package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);

    public static void main(String[] args) throws InterruptedException {
        new HWCacheDemo().demo();
    }

    private void demo() throws InterruptedException {
        HwCache<String, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        HwListener<String, Integer> listener1 = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        HwListener<String, Integer> listener2 = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key2:{}, value2:{}, action2: {}", key, value, action);
            }
        };

        HwListener<String, Integer> listener3 = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key3:{}, value3:{}, action3: {}", key, value, action);
            }
        };

        cache.addListener(listener1);
        cache.addListener(listener2);
        cache.addListener(listener3);
        cache.put("1", 1);

        logger.info("getValue:{}", cache.get("1"));
        cache.remove("1");

        listener2 = null;
        listener1 = null;
        listener3 = null;

        System.gc();

        Thread.sleep(200);
    }
}
