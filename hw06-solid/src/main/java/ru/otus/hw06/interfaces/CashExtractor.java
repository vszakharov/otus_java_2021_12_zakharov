package ru.otus.hw06.interfaces;

import java.util.Map;

import ru.otus.hw06.impl.Nominal;

public interface CashExtractor {

    Map<Nominal, Integer> extract(int sum);
}
