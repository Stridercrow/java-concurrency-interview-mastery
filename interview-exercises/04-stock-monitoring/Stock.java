package com.concurrency.mastery.interviewexercises.stockmonitoring;

/**
 * Stock — SKELETON (implement all methods)
 *
 * Represents a stock with a name, current price, and percentage increase.
 *
 * Thread Safety requirements:
 * - updatePrice() must be synchronized (read-modify-write is not atomic)
 * - getPrice() must be synchronized
 * - Price is kept as raw double (no rounding during updates)
 * - toString() rounds to 2 decimal places for display ONLY
 */
class Stock {
    // TODO: Add fields for name, price, and percentageIncrease

    /**
     * Initialize a stock with its name, starting price, and percentage increase.
     */
    public Stock(String name, double price, double percentageIncrease) {
        // TODO: Initialize fields
    }

    /**
     * Update price by applying the percentage increase.
     *
     * Formula: newPrice = currentPrice + currentPrice * (percentageIncrease / 100)
     *
     * IMPORTANT:
     * - Must be synchronized for thread safety
     * - Do NOT round the result — preserve full precision
     * - Example: 250.0 with 3% → 257.5 (not 257.50)
     */
    public void updatePrice() {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the current price (unrounded).
     */
    public double getPrice() {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    public String getName() {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Return formatted string: "StockName: $RoundedPrice"
     *
     * Price is rounded to 2 decimal places for display.
     * Use String.format(Locale.US, "%s: $%.2f", name, price)
     *
     * Example: "NFLX: $257.50"
     */
    @Override
    public String toString() {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }
}
