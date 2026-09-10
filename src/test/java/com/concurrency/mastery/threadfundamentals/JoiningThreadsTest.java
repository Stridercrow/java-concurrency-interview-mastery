package com.concurrency.mastery.threadfundamentals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 04 — Joining Threads")
class JoiningThreadsTest {

    private JoiningThreads exercise;

    @BeforeEach
    void setUp() {
        exercise = new JoiningThreads();
    }

    @Test
    @DisplayName("Should wait for all threads to complete")
    void shouldJoinAll() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch startLatch = new CountDownLatch(1);

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                try {
                    startLatch.await();
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                counter.incrementAndGet();
            });
            threads[i].start();
        }

        startLatch.countDown(); // release all threads
        exercise.joinAll(threads);

        assertEquals(5, counter.get(), "All threads should have completed");
    }

    @Test
    @DisplayName("Should detect timeout when threads don't finish")
    void shouldDetectTimeout() throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        Thread[] threads = new Thread[2];

        threads[0] = new Thread(() -> {
            try {
                startLatch.await();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        threads[1] = new Thread(() -> {
            try {
                startLatch.await();
                Thread.sleep(5000); // long sleep
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threads[0].start();
        threads[1].start();
        startLatch.countDown();

        boolean completed = exercise.joinWithTimeout(threads, 200);
        assertFalse(completed, "Should detect that not all threads finished within timeout");

        // Clean up
        threads[1].interrupt();
        threads[0].interrupt();
        threads[0].join(1000);
        threads[1].join(1000);
    }

    @Test
    @DisplayName("Should return true when all threads finish within timeout")
    void shouldReturnTrueWhenAllFinish() throws InterruptedException {
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread(() -> {
                try { Thread.sleep(20); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
            threads[i].start();
        }

        boolean completed = exercise.joinWithTimeout(threads, 5000);
        assertTrue(completed, "All threads should finish within timeout");
    }

    @Test
    @DisplayName("Should measure join time approximately")
    void shouldMeasureJoinTime() throws InterruptedException {
        Thread t = new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        t.start();

        long elapsed = exercise.joinAndMeasure(t);

        assertTrue(elapsed >= 50, "Should take at least ~50ms (measured: " + elapsed + "ms)");
        assertTrue(elapsed < 2000, "Should not take unreasonably long");
    }
}
