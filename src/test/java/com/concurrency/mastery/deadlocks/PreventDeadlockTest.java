package com.concurrency.mastery.deadlocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 28 — Prevent the Deadlock")
class PreventDeadlockTest {

    private PreventDeadlock exercise;

    @BeforeEach
    void setUp() {
        exercise = new PreventDeadlock();
    }

    @Test
    @DisplayName("Lock ordering should prevent deadlock")
    void lockOrderingShouldPreventDeadlock() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Two threads trying to acquire both locks in the same order
        executor.submit(() -> {
            try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            exercise.safeWithLockOrdering(counter::incrementAndGet);
        });
        executor.submit(() -> {
            try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            exercise.safeWithLockOrdering(counter::incrementAndGet);
        });

        latch.countDown();
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS),
            "Lock ordering should prevent deadlock — both operations should complete");

        assertEquals(2, counter.get());
    }

    @Test
    @DisplayName("tryLock should prevent deadlock and eventually succeed")
    void tryLockShouldPreventDeadlock() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                exercise.safeWithTryLock(counter::incrementAndGet);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        executor.submit(() -> {
            try {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                exercise.safeWithTryLock(counter::incrementAndGet);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        latch.countDown();
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS),
            "tryLock should prevent deadlock");

        assertEquals(2, counter.get());
    }

    @Test
    @DisplayName("Reduced scope should prevent deadlock")
    void reducedScopeShouldPreventDeadlock() throws InterruptedException {
        AtomicInteger counterA = new AtomicInteger(0);
        AtomicInteger counterB = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            exercise.safeWithReducedScope(counterA::incrementAndGet, counterB::incrementAndGet);
        });
        executor.submit(() -> {
            try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            exercise.safeWithReducedScope(counterA::incrementAndGet, counterB::incrementAndGet);
        });

        latch.countDown();
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS),
            "Reduced scope should prevent deadlock");

        assertEquals(2, counterA.get());
        assertEquals(2, counterB.get());
    }
}
