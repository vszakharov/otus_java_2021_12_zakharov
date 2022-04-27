package ru.otus.hw06.impl;

import ru.otus.hw06.interfaces.Cell;

public class CellImpl implements Cell {

    private final Nominal nominal;

    private int count;

    public CellImpl(Nominal nominal, int count) {
        this.nominal = nominal;
        this.count = count;
    }

    @Override
    public void addBanknote() {
        count++;
    }

    @Override
    public void takeBanknote() {
        if (count == 0) {
            throw new IllegalStateException("Невозможно извлечь банкноту не может быть выполнена, " +
                    "так как ячейка пуста!");
        }
        count--;
    }

    @Override
    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public int getCount() {
        return count;
    }
}
