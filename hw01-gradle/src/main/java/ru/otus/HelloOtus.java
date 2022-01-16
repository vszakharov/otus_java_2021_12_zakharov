package ru.otus;

import com.google.common.collect.Range;

public class HelloOtus {
    public static void main(String... args) {
        Range<Integer> range = Range.closed(0, 9);
        System.out.printf("Lower: %d, Upper: %d%n", range.lowerEndpoint(), range.upperEndpoint());
    }
}
