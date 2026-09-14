package com.concurrency.mastery.interviewexercises.stockmonitoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SOLUTION — Real-time Stock Price Monitoring System
 *
 * Key Concepts:
 * - Thread Safety: synchronized updatePrice() prevents race conditions
 * - ExecutorService: parallel stock updates via thread pool
 * - Decimal Precision: maintain unrounded price, round only for logging
 * - Compound Growth: each update builds on previous unrounded value
 *
 * Design Decisions:
 * 1. Stock.price is kept as raw double (no rounding during updates)
 * 2. toString() rounds to 2 decimal places for display only
 * 3. monitorStocks updates all stocks in parallel via ExecutorService
 * 4. Each stock's update is independent — no cross-stock dependencies
 */

// ============================================================
// Stock
// ============================================================
/**
 * Represents a stock with a name, current price, and percentage increase.
 *
 * Thread Safety:
 * - updatePrice() is synchronized to prevent concurrent modification
 * - getPrice() returns the raw unrounded value for precision
 * - toString() rounds for display only
 */
class Stock {
    private final String name;
    private double price;
    private final double percentageIncrease;

    public Stock(String name, double price, double percentageIncrease) {
        this.name = name;
        this.price = price;
        this.percentageIncrease = percentageIncrease;
    }

    /**
     * Update price by applying the percentage increase.
     * Synchronized to ensure atomic read-modify-write.
     *
     * Formula: newPrice = currentPrice + currentPrice * (pctIncrease / 100)
     * No rounding applied — precision is preserved for next update.
     */
    public synchronized void updatePrice() {
        price = price + price * (percentageIncrease / 100.0);
    }

    public synchronized double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns formatted string with price rounded to 2 decimal places.
     * This is the ONLY place rounding happens.
     */
    @Override
    public String toString() {
        return String.format(Locale.US, "%s: $%.2f", name, price);
    }
}

// ============================================================
// StockMonitor
// ============================================================
/**
 * Monitors stock prices, updating them each second and logging results.
 *
 * Uses ExecutorService to update stocks in parallel.
 * Each stock update is an independent task — no shared state between stocks.
 */
class StockMonitor {

    /**
     * Monitor stock prices for one second.
     *
     * For each stock:
     * 1. Call updatePrice() to apply percentage increase
     * 2. Format the log line with rounded price
     *
     * Returns log lines for the current second, starting with "Second X:" header.
     */
    public List<String> monitorStocks(int instance, int numberOfStocks,
                                       List<Stock> stocks) throws Exception {
        List<String> logs = new ArrayList<>();

        // Add header
        logs.add("Second " + instance + ":");

        // Update all stocks in parallel using ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(numberOfStocks);
        try {
            List<Callable<Void>> tasks = new ArrayList<>();
            for (Stock stock : stocks) {
                tasks.add(() -> {
                    stock.updatePrice();
                    return null;
                });
            }
            executor.invokeAll(tasks); // Wait for all updates to complete
        } finally {
            executor.shutdown();
        }

        // Log all stock prices after updates
        for (Stock stock : stocks) {
            logs.add(stock.toString());
        }

        return logs;
    }
}
