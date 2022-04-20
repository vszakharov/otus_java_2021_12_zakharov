package ru.otus.aop.proxy;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestLogging();
        testLogging.calculation(6);
        testLogging.calculation(1, 2);
        testLogging.calculation(3, 5, "7");
    }
}



