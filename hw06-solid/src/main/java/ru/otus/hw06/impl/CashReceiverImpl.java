package ru.otus.hw06.impl;

import java.util.List;

import ru.otus.hw06.interfaces.CashHolder;
import ru.otus.hw06.interfaces.CashReceiver;

public class CashReceiverImpl implements CashReceiver {
    private final CashHolder cashHolder;

    public CashReceiverImpl(CashHolder cashHolder) {
        this.cashHolder = cashHolder;
    }

    @Override
    public void receive(List<Nominal> banknotes) {
        if (banknotes == null || banknotes.isEmpty()) {
            throw new IllegalArgumentException("Банкноты отсутствуют!");
        }

        for (var banknote : banknotes) {
            cashHolder.addBanknote(banknote);
        }
    }
}
