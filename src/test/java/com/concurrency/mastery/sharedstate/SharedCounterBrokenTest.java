package com.concurrency.mastery.sharedstate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test demonstrates the race condition in SharedCounterBroken.
 * The test itself is structured to safely show the problem.
 *
 * IMPORTANT: The test verifies that the counter IS broken (i.e., it does NOT
 * produce the expected result). This is intentional — the exercise is about
 * understanding WHY it fails.
 */
@DisplayName("Exercise 15 — Shared Counter (Broken)")
class SharedCounterBrokenTest {

    private SharedCounterBroken counter;

    @BeforeEach
    void setUp() {
        counter = new SharedCounterBroken();
    }

    @Test
    @DisplayName("Single-thread increment should work correctly")
    void singleThreadShouldWork() {
        counter.incrementMultipleTimes(1000);
        assertEquals(1000, counter.getCount(),
            "Single thread should produce exact count");
    }

    @Test
    @DisplayName("Counter IS broken — race condition loses increments")
    void demonstrateRaceCondition() throws InterruptedException {
        int threadCount = 8;
        int incrementsPerThread = 10_000;
        int expectedTotal = threadCount * incrementsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // all threads start at the same time
                    counter.incrementMultipleTimes(incrementsPerThread);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // release all threads
        doneLatch.await();
        executor.shutdown();

        int actual = counter.getCount();

        // This assertion documents that the counter IS broken.
        // In many runs, actual != expectedTotal, proving the race condition.
        System.out.println("Expected: " + expectedTotal + ", Actual: " + actual);
        System.out.println("Lost " + (expectedTotal - actual) + " increments due to race condition");

        // We don't assert actual != expected because race conditions are timing-dependent.
        // Instead, we document the observation. The test passes either way.
        // The purpose is to demonstrate the race condition, not to make the test fail.
    }

    @Test
    @DisplayName("Race condition can be observed with repeated runs")
    void canObserveRaceCondition() throws InterruptedException {
        // Run the race condition test 10 times and count how many times it fails
        int failures = 0;
        for (int run = 0; run < 10; run++) {
            counter.reset();
            int threadCount = 4;
            int incrementsPerThread = 100_000;

            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(1);

            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    counter.incrementMultipleTimes(incrementsPerThread);
                });
            }

            latch.countDown();
            executor.shutdown();
            while (!executor.isTerminated()) { Thread.sleep(10); }

            if (counter.getCount() != threadCount * incrementsPerThread) {
                failures++;
            }
        }

        System.out.println("Race condition observed in " + failures + " out of 10 runs");
        // This should almost always observe at least some failures
    }
}
