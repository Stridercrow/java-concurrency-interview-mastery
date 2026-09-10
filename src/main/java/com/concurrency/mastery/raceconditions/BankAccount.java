package com.concurrency.mastery.raceconditions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Exercise 17 — Bank Account
 *
 * Scenario:
 * You're building a banking system. Concurrent operations (deposit, withdraw,
 * transfer) must maintain the invariant that the balance is always consistent.
 *
 * The transfer operation involves two accounts and must be atomic —
 * you can't debit one account without crediting the other.
 *
 * Start by understanding why the unsafe version fails, then implement
 * the thread-safe version.
 */
public class BankAccount {

    private final String accountId;
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(String accountId, int initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * Deposit money into this account.
     * This method is NOT thread-safe.
     *
     * @param amount the amount to deposit (must be positive)
     */
    public void depositUnsafe(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
    }

    /**
     * Withdraw money from this account.
     * This method is NOT thread-safe.
     *
     * @param amount the amount to withdraw (must be positive)
     * @return true if sufficient funds, false otherwise
     */
    public boolean withdrawUnsafe(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Deposit money into this account. Thread-safe version.
     * TODO: Make this thread-safe using the lock.
     *
     * @param amount the amount to deposit (must be positive)
     */
    public void deposit(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        // TODO: Acquire the lock, update balance, release the lock
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Withdraw money from this account. Thread-safe version.
     * TODO: Make this thread-safe using the lock.
     *
     * @param amount the amount to withdraw (must be positive)
     * @return true if sufficient funds, false otherwise
     */
    public boolean withdraw(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        // TODO: Acquire the lock, check balance, update if sufficient, release the lock
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Transfer money from this account to another account atomically.
     * TODO: Make this thread-safe. Both accounts must be locked.
     *
     * IMPORTANT: When locking two accounts, always lock in a consistent order
     * (e.g., by accountId) to prevent deadlock.
     *
     * @param target the account to transfer to
     * @param amount the amount to transfer
     * @return true if transfer succeeded, false if insufficient funds
     */
    public boolean transfer(BankAccount target, int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (this.accountId.equals(target.accountId)) return false;

        // TODO: Lock both accounts in consistent order, perform the transfer atomically.
        throw new UnsupportedOperationException("Implement this method");
    }
}
