package com.concurrency.mastery.raceconditions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SOLUTION — Exercise 17: Bank Account
 *
 * Key Concepts:
 * - Thread-safe deposit/withdraw using locks
 * - Atomic transfer using lock ordering to prevent deadlock
 * - Lock ordering: always acquire locks in consistent order (e.g., by accountId)
 *
 * Why the naive approach fails:
 * - balance += amount is not atomic
 * - Two threads can read the same balance and overwrite each other
 */
public class BankAccountSolution {

    private final String accountId;
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccountSolution(String accountId, int initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() { return accountId; }
    public int getBalance() { return balance; }

    public void deposit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean transfer(BankAccountSolution target, int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (this.accountId.equals(target.accountId)) return false;

        // Lock ordering by accountId to prevent deadlock
        Lock firstLock = this.accountId.compareTo(target.accountId) < 0 ? this.lock : target.lock;
        Lock secondLock = this.accountId.compareTo(target.accountId) < 0 ? target.lock : this.lock;

        firstLock.lock();
        try {
            secondLock.lock();
            try {
                if (this.balance >= amount) {
                    this.balance -= amount;
                    target.balance += amount;
                    return true;
                }
                return false;
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }
}
