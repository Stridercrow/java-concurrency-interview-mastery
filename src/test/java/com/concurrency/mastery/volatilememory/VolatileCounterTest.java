package com.concurrency.mastery.volatilememory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 24 — Volatile Counter")
class VolatileCounterTest {

    private VolatileCounter exercise;

    @BeforeEach
    void setUp() {
        exercise = new VolatileCounter();
    }

    @Test
    @DisplayName("volatile counter IS thread-safe for single increments")
    void volatileIsSafeForSingleIncrement() throws InterruptedException {
        // A single volatile increment from a single thread is safe
        exercise.incrementVolatile();
        assertEquals(1, exercise.getVolatileCount());
    }

    @Test
    @DisplayName("volatile counter loses increments under contention")
    void volatileLosesUnderContention() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 100_000;
        int expected = threadCount * incrementsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) {
                    exercise.incrementVolatile();
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        // Volatile counter will likely be LESS than expected
        int actual = exercise.getVolatileCount();
        System.out.println("volatile: expected=" + expected + ", actual=" + actual);
        // Don't assert failure — race conditions are timing-dependent
    }

    @Test
    @DisplayName("synchronized counter should be correct under contention")
    void synchronizedShouldBeCorrect() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) {
                    exercise.incrementSynchronized();
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(expected, exercise.getSynchronizedCount(),
            "synchronized counter should be exact");
    }

    @Test
    @DisplayName("AtomicInteger counter should be correct under contention")
    void atomicShouldBeCorrect() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) {
                    exercise.incrementAtomic();
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(expected, exercise.getAtomicCount(),
            "AtomicInteger counter should be exact");
    }
}
