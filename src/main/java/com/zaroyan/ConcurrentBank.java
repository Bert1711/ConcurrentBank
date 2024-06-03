package com.zaroyan;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zaroyan
 */

public class ConcurrentBank {
    private Map<Integer, BankAccount> accounts; // Хранит счета клиентов
    private Lock transferLock; // Lock для обеспечения атомарности операции перевода

    // Конструктор, инициализирует accounts как HashMap и transferLock как ReentrantLock
    public ConcurrentBank() {
        this.accounts = new HashMap<>();
        this.transferLock = new ReentrantLock();
    }

    // Метод создания нового банковского счета с начальным балансом
    public BankAccount createAccount(int initialBalance) {
        BankAccount account = new BankAccount(accounts.size() + 1, initialBalance);
        accounts.put(account.getAccountId(), account); // Добавляет счет в карту счетов
        return account; // Возвращает созданный счет
    }

    // Метод для выполнения перевода между счетами
    public void transfer(BankAccount fromAccount, BankAccount toAccount, int amount) {
        transferLock.lock(); // Блокирует доступ к операции перевода
        try {
            fromAccount.withdraw(amount); // Снимает сумму с первого счета
            toAccount.deposit(amount); // Вносит сумму на второй счет
        } finally {
            transferLock.unlock(); // Разблокирует доступ
        }
    }

    // Метод для получения общего баланса всех счетов
    public int getTotalBalance() {
        int totalBalance = 0; // Переменная для хранения общего баланса
        for (BankAccount account : accounts.values()) { // Итерирует по всем счетам
            totalBalance += account.getBalance(); // Добавляет баланс каждого счета к общему балансу
        }
        return totalBalance; // Возвращает общий баланс
    }
}