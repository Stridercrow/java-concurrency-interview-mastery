package com.concurrency.mastery.interviewexercises.stockmonitoring;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Real-time Stock Price Monitoring System.
 *
 * Run these tests against your skeleton implementation to validate correctness.
 */
class StockMonitoringTest {

    // ============================================================
    // Stock Tests
    // ============================================================

    @Test
    void testStockCreation() {
        Stock stock = new Stock("AAPL", 100.0, 2.0);
        assertEquals("AAPL", stock.getName());
        assertEquals(100.0, stock.getPrice(), 0.001);
    }

    @Test
    void testUpdatePrice() {
        Stock stock = new Stock("AAPL", 100.0, 2.0);
        stock.updatePrice();
        // 100 + 100 * 0.02 = 102.0
        assertEquals(102.0, stock.getPrice(), 0.001);
    }

    @Test
    void testUpdatePriceMultipleTimes() {
        Stock stock = new Stock("NFLX", 250.0, 3.0);
        stock.updatePrice(); // 250 + 250*0.03 = 257.5
        stock.updatePrice(); // 257.5 + 257.5*0.03 = 265.225
        stock.updatePrice(); // 265.225 + 265.225*0.03 = 273.18175
        stock.updatePrice(); // 273.18175 + 273.18175*0.03 = 281.3772025

        assertEquals(281.3772025, stock.getPrice(), 0.0000001);
    }

    @Test
    void testToStringRoundsToTwoDecimals() {
        Stock stock = new Stock("NFLX", 250.0, 3.0);
        stock.updatePrice(); // 257.5
        assertEquals("NFLX: $257.50", stock.toString());

        stock.updatePrice(); // 265.225
        assertEquals("NFLX: $265.23", stock.toString()); // rounded
    }

    @Test
    void testToStringNoRoundingOnPrice() {
        Stock stock = new Stock("GOOGL", 200.0, 1.5);
        stock.updatePrice(); // 203.0
        stock.updatePrice(); // 206.045

        // Price stored unrounded
        assertEquals(206.045, stock.getPrice(), 0.0000001);
        // But toString rounds
        assertEquals("GOOGL: $206.05", stock.toString());
    }

    @Test
    void testZeroPercentageIncrease() {
        Stock stock = new Stock("STABLE", 100.0, 0.0);
        stock.updatePrice();
        assertEquals(100.0, stock.getPrice(), 0.001);
        assertEquals("STABLE: $100.00", stock.toString());
    }

    // ============================================================
    // StockMonitor Tests
    // ============================================================

    @Test
    void testMonitorStocksSingleSecond() throws Exception {
        StockMonitor monitor = new StockMonitor();
        List<Stock> stocks = Arrays.asList(
            new Stock("AAPL", 100.0, 2.0),
            new Stock("GOOGL", 200.0, 1.5)
        );

        List<String> logs = monitor.monitorStocks(1, 2, stocks);

        assertNotNull(logs);
        assertEquals(3, logs.size()); // header + 2 stocks
        assertEquals("Second 1:", logs.get(0));
        assertEquals("AAPL: $102.00", logs.get(1));
        assertEquals("GOOGL: $203.00", logs.get(2));
    }

    @Test
    void testMonitorStocksMultipleSeconds() throws Exception {
        StockMonitor monitor = new StockMonitor();
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("NFLX", 250.0, 3.0));
        stocks.add(new Stock("DIS", 180.0, 1.8));

        // Simulate 4 seconds
        List<String> allLogs = new ArrayList<>();
        for (int sec = 1; sec <= 4; sec++) {
            allLogs.addAll(monitor.monitorStocks(sec, 2, stocks));
        }

        // Verify we have 4 headers + 8 stock lines = 12 lines
        assertEquals(12, allLogs.size());

        // Verify second 1
        assertEquals("Second 1:", allLogs.get(0));
        assertEquals("NFLX: $257.50", allLogs.get(1));
        assertEquals("DIS: $183.24", allLogs.get(2));

        // Verify second 4
        assertEquals("Second 4:", allLogs.get(9));
        assertEquals("NFLX: $281.38", allLogs.get(10));
        assertEquals("DIS: $193.31", allLogs.get(11));
    }

    @Test
    void testMonitorStocksSampleCase1() throws Exception {
        StockMonitor monitor = new StockMonitor();
        List<Stock> stocks = Arrays.asList(
            new Stock("MSFT", 300.0, 1.0),
            new Stock("TSLA", 400.0, 2.0),
            new Stock("GOOGL", 500.0, 1.5)
        );

        List<String> allLogs = new ArrayList<>();
        for (int sec = 1; sec <= 3; sec++) {
            allLogs.addAll(monitor.monitorStocks(sec, 3, stocks));
        }

        // Second 1
        assertEquals("MSFT: $303.00", allLogs.get(1));
        assertEquals("TSLA: $408.00", allLogs.get(2));
        assertEquals("GOOGL: $507.50", allLogs.get(3));

        // Second 2
        assertEquals("MSFT: $306.03", allLogs.get(5));
        assertEquals("TSLA: $416.16", allLogs.get(6));
        assertEquals("GOOGL: $515.11", allLogs.get(7));

        // Second 3
        assertEquals("MSFT: $309.09", allLogs.get(9));
        assertEquals("TSLA: $424.48", allLogs.get(10));
        assertEquals("GOOGL: $522.84", allLogs.get(11));
    }

    // ============================================================
    // Concurrency Test
    // ============================================================

    @Test
    void testConcurrentStockUpdates() throws Exception {
        StockMonitor monitor = new StockMonitor();
        Stock stock = new Stock("TEST", 100.0, 1.0);

        // Run monitorStocks 5 times concurrently
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int sec = i + 1;
            threads.add(new Thread(() -> {
                try {
                    monitor.monitorStocks(sec, 1, List.of(stock));
                } catch (Exception e) {
                    fail("Exception in thread: " + e.getMessage());
                }
            }));
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        // After 5 updates: 100 * (1.01)^5 ≈ 105.10100501
        double expected = 100.0;
        for (int i = 0; i < 5; i++) {
            expected = expected + expected * 0.01;
        }
        assertEquals(expected, stock.getPrice(), 0.0000001);
    }
}
