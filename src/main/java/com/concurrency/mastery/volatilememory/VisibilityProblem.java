package com.concurrency.mastery.volatilememory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Exercise 22 — Visibility Problem
 *
 * Scenario:
 * A worker thread runs a computation until a shutdown flag is set by the main thread.
 * Without proper synchronization, the worker may never see the flag change due to
 * CPU caching — this is the visibility problem.
 *
 * This exercise demonstrates the problem. Exercise 23 solves it with volatile.
 */
public class VisibilityProblem {

    private boolean running = true;

    /**
     * Start a worker that loops while running is true.
     * Returns a Thread reference for the worker.
     *
     * The worker increments a counter each iteration.
     *
     * @param counter a shared counter to track iterations
     * @return the worker thread
     */
    public Thread startWorker(java.util.concurrent.atomic.AtomicInteger counter) {
        Thread worker = new Thread(() -> {
            while (running) {
                counter.incrementAndGet();
            }
        });
        worker.start();
        return worker;
    }

    /**
     * Signal the worker to stop.
     * The worker may or may not see this change promptly (visibility problem).
     */
    public void shutdown() {
        running = false;
    }

    /**
     * Check if the worker should keep running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Reset for the next test.
     */
    public void reset() {
        running = true;
    }
}
