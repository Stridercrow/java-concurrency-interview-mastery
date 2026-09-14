package com.concurrency.mastery.interviewexercises.cryptocurrency;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Cryptocurrency Price Management System.
 *
 * Run these tests against your skeleton implementation to validate correctness.
 */
class CryptocurrencyPriceTest {

    // ============================================================
    // CryptoCurrencyListenerImpl Tests
    // ============================================================

    @Test
    void testInitialPrice() {
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        assertEquals("BTC", btc.getCurrencyCode());
        assertEquals(0, btc.getPrice());
    }

    @Test
    void testIncrease() {
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        btc.increase(100);
        assertEquals(100, btc.getPrice());
        btc.increase(50);
        assertEquals(150, btc.getPrice());
    }

    @Test
    void testDecrease() {
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        btc.increase(100);
        btc.decrease(50);
        assertEquals(50, btc.getPrice());
    }

    @Test
    void testUpdatePriceIncrease() {
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        LocalDateTime time = LocalDateTime.of(2024, 1, 1, 12, 0);
        btc.updatePrice("increase", 100, time);
        assertEquals(100, btc.getPrice());
    }

    @Test
    void testUpdatePriceDecrease() {
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 12, 1);
        btc.updatePrice("increase", 100, time1);
        btc.updatePrice("decrease", 50, time2);
        assertEquals(50, btc.getPrice());
    }

    @Test
    void testCommandsHistorySorted() {
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 12, 2); // later
        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 12, 0); // earlier
        LocalDateTime time3 = LocalDateTime.of(2024, 1, 1, 12, 1); // middle

        btc.updatePrice("increase", 100, time1);
        btc.updatePrice("decrease", 50, time2);
        btc.updatePrice("increase", 200, time3);

        List<String> history = btc.getCommandsHistory();
        assertEquals(3, history.size());
        // Should be sorted by timestamp: time2, time3, time1
        assertEquals("decrease 50", history.get(0));
        assertEquals("increase 200", history.get(1));
        assertEquals("increase 100", history.get(2));
    }

    @Test
    void testEmptyHistory() {
        CryptoCurrencyListenerImpl eth = new CryptoCurrencyListenerImpl("ETH");
        assertTrue(eth.getCommandsHistory().isEmpty());
    }

    // ============================================================
    // CryptoCurrencyManager Tests
    // ============================================================

    @Test
    void testRegisterAndUpdate() {
        CryptoCurrencyManager manager = new CryptoCurrencyManager();
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        CryptoCurrencyListenerImpl eth = new CryptoCurrencyListenerImpl("ETH");

        manager.registerListener(btc);
        manager.registerListener(eth);

        LocalDateTime time = LocalDateTime.now();
        manager.updatePrice("BTC", "increase", 100, time);

        assertEquals(100, btc.getPrice());
        assertEquals(0, eth.getPrice()); // ETH should not be affected
    }

    @Test
    void testMultipleUpdates() {
        CryptoCurrencyManager manager = new CryptoCurrencyManager();
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        manager.registerListener(btc);

        LocalDateTime time = LocalDateTime.now();
        manager.updatePrice("BTC", "increase", 100, time);
        manager.updatePrice("BTC", "decrease", 50, time);
        manager.updatePrice("BTC", "increase", 200, time);

        assertEquals(250, btc.getPrice());
    }

    @Test
    void testUpdateUnregisteredCurrency() {
        CryptoCurrencyManager manager = new CryptoCurrencyManager();
        // No listeners registered — should not throw
        LocalDateTime time = LocalDateTime.now();
        assertDoesNotThrow(() -> manager.updatePrice("BTC", "increase", 100, time));
    }

    // ============================================================
    // Concurrency Test
    // ============================================================

    @Test
    void testConcurrentUpdates() throws InterruptedException {
        CryptoCurrencyManager manager = new CryptoCurrencyManager();
        CryptoCurrencyListenerImpl btc = new CryptoCurrencyListenerImpl("BTC");
        manager.registerListener(btc);

        int threadCount = 10;
        int incrementsPerThread = 1000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    manager.updatePrice("BTC", "increase", 1, LocalDateTime.now());
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        // Expected: 10 threads × 1000 increments = 10000
        assertEquals(threadCount * incrementsPerThread, btc.getPrice());
    }
}
