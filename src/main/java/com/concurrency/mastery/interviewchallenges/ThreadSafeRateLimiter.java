package com.concurrency.mastery.interviewchallenges;

import java.util.concurrent.Semaphore;

/**
 * Challenge 2 — Thread-Safe Rate Limiter
 *
 * Problem:
 * Design a thread-safe rate limiter that allows at most N concurrent operations.
 * Additional requests should block until a permit is available.
 *
 * Constraints:
 * - Must be thread-safe
 * - Must support configurable concurrency limit
 * - Must support try-acquire (non-blocking attempt)
 * - Must support timeout-based acquisition
 *
 * Acceptance Criteria:
 * - At most N operations can run concurrently
 * - Extra requests block until permits are available
 * - tryAcquire returns false immediately if no permits
 * - acquireWithTimeout returns false after timeout
 * - Permits are properly released
 *
 * Interview Discussion:
 * - What concurrency primitive would you choose?
 * - What are the alternatives?
 * - How does this compare to a thread pool?
 */
public class ThreadSafeRateLimiter {

    private final Semaphore semaphore;

    public ThreadSafeRateLimiter(int maxConcurrent) {
        this.semaphore = new Semaphore(maxConcurrent);
    }

    /**
     * Acquire a permit. Blocks until one is available.
     */
    public void acquire() throws InterruptedException {
        // TODO: Acquire a semaphore permit
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Try to acquire a permit without blocking.
     *
     * @return true if a permit was acquired, false otherwise
     */
    public boolean tryAcquire() {
        // TODO: Try to acquire a permit without blocking
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Try to acquire a permit with a timeout.
     *
     * @param timeoutMs the maximum time to wait
     * @return true if a permit was acquired within the timeout
     */
    public boolean acquireWithTimeout(long timeoutMs) throws InterruptedException {
        // TODO: Try to acquire a permit with a timeout
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Release a previously acquired permit.
     */
    public void release() {
        // TODO: Release a semaphore permit
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the number of available permits.
     */
    public int availablePermits() {
        return semaphore.availablePermits();
    }
}
