package com.concurrency.mastery.synchronization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 19 — synchronized Method vs Block")
class SynchronizedMethodVsBlockTest {

    private SynchronizedMethodVsBlock exercise;

    @BeforeEach
    void setUp() {
        exercise = new SynchronizedMethodVsBlock();
    }

    @Test
    @DisplayName("Method-level synchronization should be thread-safe")
    void methodLevelShouldBeThreadSafe() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) {
                    exercise.incrementMethodLevel();
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(expected, exercise.getValueMethodLevel());
    }

    @Test
    @DisplayName("Block-level synchronization should be thread-safe")
    void blockLevelShouldBeThreadSafe() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) {
                    exercise.incrementBlockLevel();
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertEquals(expected, exercise.getValueBlockLevel());
    }

    @Test
    @DisplayName("Both approaches should produce the same result")
    void bothApproachesShouldAgree() throws InterruptedException {
        int threadCount = 4;
        int incrementsPerThread = 5_000;

        // Test method-level
        ExecutorService executor1 = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch1 = new CountDownLatch(1);
        for (int i = 0; i < threadCount; i++) {
            executor1.submit(() -> {
                try { latch1.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) exercise.incrementMethodLevel();
            });
        }
        latch1.countDown();
        executor1.shutdown();
        while (!executor1.isTerminated()) { Thread.sleep(10); }

        int methodResult = exercise.getValueMethodLevel();

        exercise.reset();

        // Test block-level
        ExecutorService executor2 = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch2 = new CountDownLatch(1);
        for (int i = 0; i < threadCount; i++) {
            executor2.submit(() -> {
                try { latch2.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) exercise.incrementBlockLevel();
            });
        }
        latch2.countDown();
        executor2.shutdown();
        while (!executor2.isTerminated()) { Thread.sleep(10); }

        int blockResult = exercise.getValueBlockLevel();

        assertEquals(methodResult, blockResult,
            "Both synchronization approaches should produce the same result");
    }

    @Test
    @DisplayName("Atomic update should keep fields consistent")
    void atomicUpdateShouldBeConsistent() throws InterruptedException {
        exercise.reset();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                exercise.updateNameAndValue("updated", 1);
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        // After all operations complete, name should be "updated" and value should be 1000
        assertEquals("updated", exercise.getName());
        assertEquals(1000, exercise.getValue());
    }
}
