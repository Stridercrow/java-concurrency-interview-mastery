package com.concurrency.mastery.interviewexercises.cryptocurrency;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CryptoCurrencyListenerImpl — SKELETON (implement all methods)
 *
 * Maintains current price and command history for a single cryptocurrency.
 *
 * Thread Safety requirements:
 * - Use a single lock object to protect all mutable state (price + history)
 * - Avoid redundant synchronized on individual methods — use synchronized blocks
 * - History list must handle concurrent writes from multiple threads
 * - getCommandsHistory() must return sorted results
 *
 * Inner class hint: you may need a PriceChange class to track action + timestamp.
 */
class CryptoCurrencyListenerImpl implements CryptoCurrencyListener {
    // TODO: Add fields for cryptoCurrency, price, history, and a lock object
    // Hint: private final Object lock = new Object();

    public CryptoCurrencyListenerImpl(String cryptoCurrency) {
        // TODO: Initialize fields (price starts at 0)
    }

    /**
     * Increase the price by the given amount.
     * Use synchronized(lock) block for thread safety.
     */
    @Override
    public void increase(int amount) {
        // TODO: Implement this method using synchronized(lock)
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Decrease the price by the given amount.
     * Use synchronized(lock) block for thread safety.
     */
    @Override
    public void decrease(int amount) {
        // TODO: Implement this method using synchronized(lock)
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Update price based on action ("increase" or "decrease") and record
     * the change with the given timestamp.
     *
     * Use a SINGLE synchronized(lock) block to ensure:
     * 1. Price update is atomic
     * 2. History entry is recorded consistently
     * 3. No redundant reentrant locking
     *
     * Steps inside the synchronized block:
     * 1. If action is "increase", price += amount
     * 2. If action is "decrease", price -= amount
     * 3. Add new PriceChange(action, amount, currentTime) to history
     */
    @Override
    public void updatePrice(String action, int amount, LocalDateTime currentTime) {
        // TODO: Implement this method using a single synchronized(lock) block
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public String getCurrencyCode() {
        // TODO: Implement this method (no sync needed — immutable field)
        throw new UnsupportedOperationException("Implement this method");
    }

    @Override
    public int getPrice() {
        // TODO: Implement this method using synchronized(lock)
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Return command history sorted by timestamp.
     * Each entry formatted as "action amount" (e.g., "increase 100").
     * CopyOnWriteArrayList is safe for concurrent iteration — no sync needed.
     */
    @Override
    public List<String> getCommandsHistory() {
        // TODO: Implement this method (no sync needed for CopyOnWriteArrayList)
        throw new UnsupportedOperationException("Implement this method");
    }
}
