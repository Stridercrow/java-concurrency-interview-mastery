package com.concurrency.mastery.sharedstate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 16 — Shared Counter Solutions")
class SharedCounterSolutionsTest {

    private SharedCounterSolutions exercise;

    @BeforeEach
    void setUp() {
        exercise = new SharedCounterSolutions();
    }

    @Test
    @DisplayName("synchronized approach should be thread-safe")
    void synchronizedShouldBeThreadSafe() throws InterruptedException {
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
            "synchronized should produce exact count");
    }

    @Test
    @DisplayName("AtomicInteger approach should be thread-safe")
    void atomicShouldBeThreadSafe() throws InterruptedException {
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
            "AtomicInteger should produce exact count");
    }

    @Test
    @DisplayName("Thread-local approach should be thread-safe")
    void threadLocalShouldBeThreadSafe() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        int total = exercise.incrementWithoutSharedState(incrementsPerThread, threadCount);

        assertEquals(expected, total,
            "Thread-local approach should produce exact count");
    }

    @Test
    @DisplayName("All approaches should produce the same result")
    void allApproachesShouldAgree() throws InterruptedException {
        exercise.reset();
        int threadCount = 4;
        int incrementsPerThread = 5_000;
        int expected = threadCount * incrementsPerThread;

        // Test synchronized
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) exercise.incrementSynchronized();
            });
        }
        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        int syncResult = exercise.getSynchronizedCount();

        exercise.reset();
        int localResult = exercise.incrementWithoutSharedState(incrementsPerThread, threadCount);

        assertEquals(expected, syncResult, "synchronized should match expected");
        assertEquals(expected, localResult, "thread-local should match expected");
        assertEquals(syncResult, localResult, "All approaches should produce the same result");
    }
}
