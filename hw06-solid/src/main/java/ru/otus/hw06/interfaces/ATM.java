package ru.otus.hw06.interfaces;

import java.util.List;
import java.util.Map;

import ru.otus.hw06.impl.Nominal;

public interface ATM {

    void deposit(List<Nominal> banknotes);

    Map<Nominal, Integer> withdraw(int sum);

    int balance();
}
