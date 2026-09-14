package com.concurrency.mastery.interviewexercises.stockmonitoring;

import java.util.List;

/**
 * StockMonitor — SKELETON (implement monitorStocks)
 *
 * Monitors stock prices, updating them each second and logging results.
 *
 * Uses ExecutorService to update stocks in parallel.
 * Each stock update is an independent task — no cross-stock dependencies.
 *
 * Output format for each second:
 *   "Second X:"
 *   "StockName1: $Price1"
 *   "StockName2: $Price2"
 *   ...
 */
class StockMonitor {

    /**
     * Monitor stock prices for one second.
     *
     * Steps:
     * 1. Create an ExecutorService with numberOfStocks threads
     * 2. Submit a task for each stock that calls stock.updatePrice()
     * 3. Wait for all tasks to complete (invokeAll or similar)
     * 4. Shut down the executor
     * 5. Build log lines:
     *    - First line: "Second <instance>:"
     *    - Following lines: stock.toString() for each stock
     * 6. Return the list of log lines
     *
     * @param instance the current second number (1, 2, 3, ...)
     * @param numberOfStocks total number of stocks
     * @param stocks list of Stock objects to update
     * @return list of log strings for this second
     */
    public List<String> monitorStocks(int instance, int numberOfStocks,
                                       List<Stock> stocks) throws Exception {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Implement this method");
    }
}
