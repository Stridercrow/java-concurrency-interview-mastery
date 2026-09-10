package com.concurrency.mastery.interviewchallenges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Challenge 2 — Thread-Safe Rate Limiter")
class ThreadSafeRateLimiterTest {

    private ThreadSafeRateLimiter exercise;

    @BeforeEach
    void setUp() {
        exercise = new ThreadSafeRateLimiter(3); // max 3 concurrent
    }

    @Test
    @DisplayName("Should acquire and release permits")
    void shouldAcquireAndRelease() throws InterruptedException {
        exercise.acquire();
        exercise.acquire();
        exercise.acquire();

        assertEquals(0, exercise.availablePermits());

        exercise.release();
        assertEquals(1, exercise.availablePermits());
    }

    @Test
    @DisplayName("tryAcquire should return false when no permits available")
    void tryAcquireShouldReturnFalse() throws InterruptedException {
        exercise.acquire();
        exercise.acquire();
        exercise.acquire();

        assertFalse(exercise.tryAcquire(), "Should return false when no permits");
    }

    @Test
    @DisplayName("tryAcquire should return true when permits available")
    void tryAcquireShouldReturnTrue() {
        assertTrue(exercise.tryAcquire());
    }

    @Test
    @DisplayName("acquireWithTimeout should respect timeout")
    void acquireWithTimeoutShouldRespectTimeout() throws InterruptedException {
        exercise.acquire();
        exercise.acquire();
        exercise.acquire();

        long start = System.currentTimeMillis();
        boolean acquired = exercise.acquireWithTimeout(100);
        long elapsed = System.currentTimeMillis() - start;

        assertFalse(acquired);
        assertTrue(elapsed >= 80, "Should wait approximately the timeout");
    }

    @Test
    @DisplayName("Should limit concurrent access")
    void shouldLimitConcurrency() throws InterruptedException {
        int threadCount = 10;
        AtomicInteger concurrent = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);
        CountDownLatch allStarted = new CountDownLatch(1);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    exercise.acquire();
                    int current = concurrent.incrementAndGet();
                    maxConcurrent.updateAndGet(prev -> Math.max(prev, current));
                    allStarted.countDown();
                    Thread.sleep(50);
                    concurrent.decrementAndGet();
                    exercise.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(200);
        allStarted.countDown();
        executor.shutdown();
        while (!executor.isTerminated()) { Thread.sleep(10); }

        assertTrue(maxConcurrent.get() <= 3,
            "Max concurrent should not exceed limit (got " + maxConcurrent.get() + ")");
    }
}
