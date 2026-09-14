package com.concurrency.mastery.interviewexercises.cryptocurrency;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * CryptoCurrencyManager — SKELETON (implement all methods)
 *
 * Manages cryptocurrency listeners and facilitates concurrent notification.
 *
 * Thread Safety requirements:
 * - Listener registration must be thread-safe
 * - Listener lookup by currency code must be thread-safe
 * - Consider using ConcurrentHashMap for the listener map
 */
class CryptoCurrencyManager {
    // TODO: Add a thread-safe map to store listeners keyed by currency code

    public CryptoCurrencyManager() {
        // TODO: Initialize the listener map
    }

    /**
     * Register a listener. Use the listener's getCurrencyCode() as the key.
     */
    public void registerListener(CryptoCurrencyListener listener) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Update the price of a cryptocurrency by notifying the matching listener.
     *
     * Steps:
     * 1. Look up the listener by cryptoCurrency code
     * 2. If found, call listener.updatePrice(action, amount, currentTime)
     * 3. If not found, do nothing
     */
    public void updatePrice(String cryptoCurrency, String action, int amount,
                            LocalDateTime currentTime) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }
}
