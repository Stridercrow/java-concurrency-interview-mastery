package com.concurrency.mastery.coordination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 32 — Semaphore
 *
 * Scenario:
 * You're building a connection pool for a database. Only N connections
 * can be active at once. A Semaphore controls access to the limited resource.
 *
 * Mental model: "Limit concurrency to N."
 */
public class SemaphoreExercise {

    private final Semaphore semaphore;
    private final AtomicInteger activeCount = new AtomicInteger(0);
    private final AtomicInteger maxActiveCount = new AtomicInteger(0);

    public SemaphoreExercise(int maxPermits) {
        this.semaphore = new Semaphore(maxPermits);
    }

    /**
     * Acquire a permit, perform work, and release the permit.
     * Tracks the maximum number of concurrent permits held.
     *
     * @param task the work to perform
     */
    public void executeWithPermit(Runnable task) throws InterruptedException {
        // TODO: Acquire a permit, run the task, release in finally block.
        // Track the maximum number of concurrent active permits.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run multiple tasks with a limited number of concurrent permits.
     *
     * @param tasks the tasks to run
     * @return the results from each task
     */
    public <T> List<T> runWithConcurrencyLimit(List<java.util.concurrent.Callable<T>> tasks)
            throws InterruptedException {
        // TODO: For each task, acquire a permit, submit to an executor,
        // and release the permit after completion.
        // Collect and return all results.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Get the maximum number of concurrent permits observed.
     */
    public int getMaxActiveCount() {
        return maxActiveCount.get();
    }

    /**
     * Get the current number of available permits.
     */
    public int availablePermits() {
        return semaphore.availablePermits();
    }
}
