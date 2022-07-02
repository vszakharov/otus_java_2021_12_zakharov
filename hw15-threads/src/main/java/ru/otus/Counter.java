package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {
    private static final Logger logger = LoggerFactory.getLogger(Counter.class);
    private static final int THREAD_COUNT = 2;
    private static final int START = 1;
    private static final int END = 10;

    int count = START;
    private String currentThread = "thread-0";
    private Direction direction = Direction.UP;

    private synchronized void action() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                String threadName = Thread.currentThread().getName();
                while (!currentThread.equals(threadName)) {
                    this.wait();
                }
                logger.info("Thread: {}, count: {}", threadName, count);
                if (threadName.equals("thread-" + (THREAD_COUNT - 1))) {
                    if (count == END) {
                        direction = Direction.DOWN;
                    } else if (count == START) {
                        direction = Direction.UP;
                    }
                    count = direction == Direction.UP ? count + 1 : count - 1;
                }
                currentThread = getNextThreadName(threadName);
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(counter::action, "thread-" + i).start();
        }
    }

    private static String getNextThreadName(String currentName) {
        var currentId = Integer.parseInt(currentName.split("-")[1]);
        var nextId =  (currentId + 1) % THREAD_COUNT;
        return "thread-" + nextId;
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
