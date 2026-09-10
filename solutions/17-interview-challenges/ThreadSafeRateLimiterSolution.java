package com.concurrency.mastery.interviewchallenges;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * SOLUTION — Challenge 2: Thread-Safe Rate Limiter
 *
 * Key Concepts:
 * - Semaphore controls access to limited resources
 * - acquire() blocks, tryAcquire() doesn't
 * - tryAcquire(timeout) respects timeout
 *
 * Why Semaphore:
 * - Perfect for "limit to N concurrent" pattern
 * - Built-in blocking and non-blocking acquisition
 * - Thread-safe by design
 */
public class ThreadSafeRateLimiterSolution {

    private final Semaphore semaphore;

    public ThreadSafeRateLimiterSolution(int maxConcurrent) {
        this.semaphore = new Semaphore(maxConcurrent);
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public boolean acquireWithTimeout(long timeoutMs) throws InterruptedException {
        return semaphore.tryAcquire(timeoutMs, TimeUnit.MILLISECONDS);
    }

    public void release() {
        semaphore.release();
    }

    public int availablePermits() {
        return semaphore.availablePermits();
    }
}
