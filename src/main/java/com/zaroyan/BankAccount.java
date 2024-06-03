package com.zaroyan;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Zaroyan
 */

public class BankAccount {
    private int accountId;
    private int balance;
    private Lock lock;

    public BankAccount(int accountId, int initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }

    // Метод для внесения средств на счет
    public void deposit(int amount) {
        lock.lock(); // Захватывает lock для синхронизации доступа
        try {
            balance += amount; // Увеличивает баланс на сумму
        } finally {
            lock.unlock(); // Освобождает lock после завершения операции
        }
    }

    // Метод для снятия средств со счета
    public void withdraw(int amount) {
        lock.lock(); // Захватывает lock для синхронизации доступа
        try {
            if (balance >= amount) {
                balance -= amount; // Уменьшает баланс на сумму, если есть достаточно средств
            } else {
                throw new IllegalArgumentException("Insufficient balance"); // Иначе выбрасывает исключение
            }
        } finally {
            lock.unlock(); // Освобождает lock после завершения операции
        }
    }

    // Метод для получения текущего баланса на счете
    public int getBalance() {
        return balance;
    }

    // Метод для получения уникального идентификатора счета
    public int getAccountId() {
        return accountId;
    }
}
