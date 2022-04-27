package ru.otus.hw06.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ru.otus.hw06.interfaces.CashHolder;
import ru.otus.hw06.interfaces.Cell;

public class CashHolderImpl implements CashHolder {

    private final Map<Nominal, Cell> cells = new HashMap<>();

    public CashHolderImpl() {
        //В начальный момент времени ячейки всех номиналов пустые
        for (var nominal : Nominal.values()) {
            var cell = new CellImpl(nominal, 0);
            cells.put(nominal, cell);
        }
    }

    @Override
    public void addBanknote(Nominal nominal) {
        if (!cells.containsKey(nominal)) {
            throw new IllegalStateException("Ячейка с данным номиналом отсутствует в хранилище банкомата!");
        }

        var cell = cells.get(nominal);
        cell.addBanknote();
    }

    @Override
    public void takeBanknote(Nominal nominal) {
        if (!cells.containsKey(nominal)) {
            throw new IllegalStateException("Ячейка с данным номиналом отсутствует в хранилище банкомата!");
        }

        var cell = cells.get(nominal);
        cell.takeBanknote();
    }

    @Override
    public Collection<Cell> getCellsInfo() {
        return cells.values();
    }
}
