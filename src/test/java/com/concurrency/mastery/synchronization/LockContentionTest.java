package com.concurrency.mastery.synchronization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 21 — Lock Contention")
class LockContentionTest {

    private LockContention exercise;

    @BeforeEach
    void setUp() {
        exercise = new LockContention();
    }

    @Test
    @DisplayName("Method-level lock should be thread-safe")
    void methodLockShouldBeSafe() throws InterruptedException {
        exercise.reset();
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        runConcurrentTest(exercise::incrementMethodLock, threadCount, incrementsPerThread);
        assertEquals(expected, exercise.getValue());
    }

    @Test
    @DisplayName("Block-level lock should be thread-safe")
    void blockLockShouldBeSafe() throws InterruptedException {
        exercise.reset();
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        runConcurrentTest(exercise::incrementBlockLock, threadCount, incrementsPerThread);
        assertEquals(expected, exercise.getValue());
    }

    @Test
    @DisplayName("AtomicInteger should be thread-safe")
    void atomicShouldBeSafe() throws InterruptedException {
        exercise.reset();
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expected = threadCount * incrementsPerThread;

        runConcurrentTest(exercise::incrementAtomic, threadCount, incrementsPerThread);
        assertEquals(expected, exercise.getAtomicValue());
    }

    @Test
    @DisplayName("All thread-safe approaches should produce the same result")
    void allApproachesShouldAgree() throws InterruptedException {
        exercise.reset();
        int threadCount = 4;
        int incrementsPerThread = 5_000;
        int expected = threadCount * incrementsPerThread;

        // Method lock
        runConcurrentTest(exercise::incrementMethodLock, threadCount, incrementsPerThread);
        int methodResult = exercise.getValue();

        exercise.reset();

        // Block lock
        runConcurrentTest(exercise::incrementBlockLock, threadCount, incrementsPerThread);
        int blockResult = exercise.getValue();

        exercise.reset();

        // Atomic
        runConcurrentTest(exercise::incrementAtomic, threadCount, incrementsPerThread);
        int atomicResult = exercise.getAtomicValue();

        assertEquals(expected, methodResult);
        assertEquals(expected, blockResult);
        assertEquals(expected, atomicResult);
    }

    private void runConcurrentTest(Runnable incrementer, int threadCount, int incrementsPerThread)
            throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                for (int j = 0; j < incrementsPerThread; j++) {
                    incrementer.run();
                }
            });
        }

        latch.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }
    }
}
