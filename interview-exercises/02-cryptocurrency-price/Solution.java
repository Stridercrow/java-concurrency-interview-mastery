package com.concurrency.mastery.interviewexercises.cryptocurrency;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SOLUTION — Cryptocurrency Price Management System
 *
 * Key Concepts:
 * - Observer Pattern: listeners register with a manager, receive notifications
 * - Thread Safety: CopyOnWriteArrayList for listeners, synchronized price updates
 * - ExecutorService: concurrent command execution via thread pool
 * - Timestamped History: each change recorded with LocalDateTime for ordering
 * - ConcurrentHashMap: thread-safe listener lookup by currency code
 *
 * Design Decisions:
 * 1. CopyOnWriteArrayList for listeners — safe for concurrent iteration
 * 2. Synchronized on listener instance — each currency updated independently
 * 3. CopyOnWriteArrayList for history — safe for concurrent writes + reads
 * 4. ConcurrentHashMap for manager's listener map — lock-free lookups
 */

// ============================================================
// Listener Interface (provided)
// ============================================================
interface CryptoCurrencyListener {
    void increase(int amount);
    void decrease(int amount);
    void updatePrice(String action, int amount, LocalDateTime currentTime);
    String getCurrencyCode();
    int getPrice();
    List<String> getCommandsHistory();
}

// ============================================================
// CryptoCurrencyListenerImpl
// ============================================================
/**
 * Maintains current price and command history for a single cryptocurrency.
 *
 * Thread Safety:
 * - Single lock object for all mutable state (price + history)
 * - CopyOnWriteArrayList for history — safe for concurrent reads
 * - History entries are sorted by timestamp before returning
 *
 * Why one lock instead of synchronized on each method:
 * - updatePrice() calls increase()/decrease() — if all are synchronized,
 *   it's redundant reentrant locking (same monitor)
 * - A single lock makes the critical section explicit and avoids confusion
 * - Better for complex operations that touch multiple fields atomically
 */
class CryptoCurrencyListenerImpl implements CryptoCurrencyListener {
    private final String cryptoCurrency;
    private final Object lock = new Object(); // Single lock for all mutable state
    private int price = 0;
    private final CopyOnWriteArrayList<PriceChange> history = new CopyOnWriteArrayList<>();

    public CryptoCurrencyListenerImpl(String cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    @Override
    public void increase(int amount) {
        synchronized (lock) {
            price += amount;
        }
    }

    @Override
    public void decrease(int amount) {
        synchronized (lock) {
            price -= amount;
        }
    }

    /**
     * Updates price based on action and records the change with timestamp.
     *
     * Single synchronized block ensures:
     * 1. Price update is atomic (read-modify-write)
     * 2. History entry is recorded consistently with the new price
     * 3. No redundant reentrant locking (increase/decrease don't re-lock)
     */
    @Override
    public void updatePrice(String action, int amount, LocalDateTime currentTime) {
        synchronized (lock) {
            if ("increase".equals(action)) {
                price += amount;
            } else if ("decrease".equals(action)) {
                price -= amount;
            }
            history.add(new PriceChange(action, amount, currentTime));
        }
    }

    @Override
    public String getCurrencyCode() {
        return cryptoCurrency; // Immutable — no sync needed
    }

    @Override
    public int getPrice() {
        synchronized (lock) {
            return price;
        }
    }

    /**
     * Returns command history sorted by timestamp.
     * CopyOnWriteArrayList is safe for concurrent iteration.
     */
    @Override
    public List<String> getCommandsHistory() {
        return history.stream()
            .sorted(Comparator.comparing(PriceChange::getTime))
            .map(change -> change.getAction() + " " + change.getAmount())
            .toList();
    }

    /**
     * Inner class to track a price change with its timestamp.
     */
    private static class PriceChange {
        private final String action;
        private final int amount;
        private final LocalDateTime time;

        PriceChange(String action, int amount, LocalDateTime time) {
            this.action = action;
            this.amount = amount;
            this.time = time;
        }

        String getAction() { return action; }
        int getAmount() { return amount; }
        LocalDateTime getTime() { return time; }
    }
}

// ============================================================
// CryptoCurrencyManager
// ============================================================
/**
 * Manages cryptocurrency listeners and facilitates concurrent notification.
 *
 * Uses ConcurrentHashMap for thread-safe listener registration and lookup.
 * When updatePrice is called, it finds the matching listener by currency code
 * and notifies it with the action, amount, and timestamp.
 */
class CryptoCurrencyManager {
    private final Map<String, CryptoCurrencyListener> listeners = new ConcurrentHashMap<>();

    /**
     * Register a listener. The listener's currency code is used as the key.
     */
    public void registerListener(CryptoCurrencyListener listener) {
        listeners.put(listener.getCurrencyCode(), listener);
    }

    /**
     * Update the price of a cryptocurrency by notifying the matching listener.
     *
     * Thread Safety:
     * - ConcurrentHashMap provides atomic get
     * - Listener's updatePrice is synchronized on the listener instance
     */
    public void updatePrice(String cryptoCurrency, String action, int amount,
                            LocalDateTime currentTime) {
        CryptoCurrencyListener listener = listeners.get(cryptoCurrency);
        if (listener != null) {
            listener.updatePrice(action, amount, currentTime);
        }
    }
}
