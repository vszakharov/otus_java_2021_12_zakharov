package ru.otus.hw06;

import java.util.List;

import ru.otus.hw06.impl.ATMImpl;
import ru.otus.hw06.interfaces.ATM;

import static ru.otus.hw06.impl.Nominal.FIFTY;
import static ru.otus.hw06.impl.Nominal.FIVE_HUNDRED;
import static ru.otus.hw06.impl.Nominal.FIVE_THOUSAND;
import static ru.otus.hw06.impl.Nominal.ONE_HUNDRED;
import static ru.otus.hw06.impl.Nominal.ONE_THOUSAND;

public class ATMDemo {
    public static void main(String... args) {
        ATM atm = new ATMImpl();

        atm.deposit(List.of(ONE_HUNDRED, ONE_HUNDRED, FIVE_HUNDRED, ONE_THOUSAND, FIVE_THOUSAND, FIFTY));
        System.out.println(atm.balance()); //6750
        atm.withdraw(6000);
        System.out.println(atm.balance()); //750
        atm.deposit(List.of(ONE_HUNDRED, FIFTY));
        System.out.println(atm.balance()); //900
    }
}
