package ru.otus.hw06.interfaces;

import ru.otus.hw06.impl.Nominal;

public interface Cell {
    void addBanknote();

    void takeBanknote();

    Nominal getNominal();

    int getCount();
}
