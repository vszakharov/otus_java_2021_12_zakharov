package ru.otus.hw06.impl;

import java.util.List;
import java.util.Map;

import ru.otus.hw06.interfaces.ATM;
import ru.otus.hw06.interfaces.CashExtractor;
import ru.otus.hw06.interfaces.CashHolder;
import ru.otus.hw06.interfaces.CashReceiver;

public class ATMImpl implements ATM {
    private final CashHolder cashHolder;
    private final CashExtractor cashExtractor;
    private final CashReceiver cashReceiver;

    public ATMImpl() {
        this.cashHolder = new CashHolderImpl();
        this.cashExtractor = new MinCountCashExtractor(cashHolder);
        this.cashReceiver = new CashReceiverImpl(cashHolder);
    }

    @Override
    public void deposit(List<Nominal> banknotes) {
        cashReceiver.receive(banknotes);
    }

    @Override
    public Map<Nominal, Integer> withdraw(int sum) {
        return cashExtractor.extract(sum);
    }

    @Override
    public int balance() {
        var cells = cashHolder.getCellsInfo();
        int balance = 0;
        for (var cell : cells) {
            balance += cell.getNominal().getValue() * cell.getCount();
        }

        return balance;
    }
}
