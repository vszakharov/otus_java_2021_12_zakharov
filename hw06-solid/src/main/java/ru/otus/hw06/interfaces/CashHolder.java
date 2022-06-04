package ru.otus.hw06.interfaces;

import java.util.Collection;

import ru.otus.hw06.impl.Nominal;

public interface CashHolder {

    void addBanknote(Nominal banknote);

    void takeBanknote(Nominal banknote);

    Collection<Cell> getCellsInfo();
}
