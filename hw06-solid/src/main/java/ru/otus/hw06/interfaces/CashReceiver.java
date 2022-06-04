package ru.otus.hw06.interfaces;

import java.util.List;

import ru.otus.hw06.impl.Nominal;

public interface CashReceiver {

    void receive(List<Nominal> banknotes);
}
