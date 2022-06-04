package ru.otus.hw06.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import ru.otus.hw06.interfaces.CashExtractor;
import ru.otus.hw06.interfaces.CashHolder;
import ru.otus.hw06.interfaces.Cell;

public class MinCountCashExtractor implements CashExtractor {
    private final CashHolder cashHolder;

    public MinCountCashExtractor(CashHolder cashHolder) {
        this.cashHolder = cashHolder;
    }

    @Override
    public Map<Nominal, Integer> extract(int sum) {
        Map<Nominal, Integer> result = new HashMap<>();
        var cells = cashHolder.getCellsInfo();
        var sortedCells = cells.stream()
                .sorted(
                        Comparator.comparingInt((Cell cell) -> cell.getNominal().getValue())
                                .reversed()
                )
                .collect(Collectors.toList());

        for (var cell : sortedCells) {
            int nominal = cell.getNominal().getValue();
            int currentCount = cell.getCount();
            if (sum < nominal) {
                continue;
            }
            int banknoteCount = Math.min(sum / nominal, currentCount);
            sum -= nominal * banknoteCount;
            result.put(cell.getNominal(), banknoteCount);
            while (banknoteCount != 0) {
                cashHolder.takeBanknote(cell.getNominal());
                banknoteCount--;
            }
        }

        if (sum != 0) {
            //Если выяснили что указанную сумму извлечь нельзя, то возвращаем банкноты в хранилище
            for (var entry : result.entrySet()) {
                var banknote = entry.getKey();
                int count = entry.getValue();
                while (count > 0) {
                    cashHolder.addBanknote(banknote);
                    count--;
                }
            }

            throw new RuntimeException("Невозможно выдать указанную сумму, попробуйте ввести другую!");
        }

        return result;
    }
}
