package com.concurrency.mastery.raceconditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 18 — Inventory Reservation")
class InventoryReservationTest {

    @Test
    @DisplayName("Should reserve when stock is sufficient")
    void shouldReserveWhenSufficient() {
        InventoryReservation inv = new InventoryReservation("SKU-001", 10);
        assertTrue(inv.reserve(5));
        assertEquals(5, inv.getStock());
    }

    @Test
    @DisplayName("Should reject when stock is insufficient")
    void shouldRejectWhenInsufficient() {
        InventoryReservation inv = new InventoryReservation("SKU-001", 3);
        assertFalse(inv.reserve(5));
        assertEquals(3, inv.getStock());
    }

    @Test
    @DisplayName("Should never allow overselling under concurrent access")
    void shouldNeverOversell() throws InterruptedException {
        InventoryReservation inv = new InventoryReservation("SKU-001", 100);
        int threadCount = 20;
        int reserveAmount = 10; // each thread wants 10

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                if (inv.reserve(reserveAmount)) {
                    successCount.incrementAndGet();
                } else {
                    failCount.incrementAndGet();
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        int totalReserved = successCount.get() * reserveAmount;
        assertEquals(100, totalReserved, "Should not reserve more than stock");
        assertEquals(0, inv.getStock(), "Stock should be zero");
        assertTrue(failCount.get() > 0, "Some reservations should have failed");
    }

    @Test
    @DisplayName("Should handle cancelReservation correctly")
    void shouldCancelReservation() {
        InventoryReservation inv = new InventoryReservation("SKU-001", 10);
        inv.reserve(8);
        assertEquals(2, inv.getStock());

        inv.cancelReservation(5);
        assertEquals(7, inv.getStock());
    }

    @Test
    @DisplayName("Stock should never go negative under concurrent access")
    void stockShouldNeverGoNegative() throws InterruptedException {
        InventoryReservation inv = new InventoryReservation("SKU-001", 50);
        int threadCount = 10;
        int reserveAmount = 10;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                inv.reserve(reserveAmount);
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertTrue(inv.getStock() >= 0,
            "Stock should never go negative (got " + inv.getStock() + ")");
    }
}
