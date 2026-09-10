package com.concurrency.mastery.volatilememory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Exercise 23 — volatile Shutdown Flag
 *
 * Scenario:
 * Fix the visibility problem from Exercise 22 using volatile.
 * The worker thread must see the shutdown signal immediately.
 */
public class VolatileShutdownFlag {

    // TODO: Add the volatile keyword to make this visible across threads
    private boolean running = true;

    /**
     * Start a worker that loops while running is true.
     * The worker increments a counter each iteration.
     *
     * @param counter a shared counter to track iterations
     * @return the worker thread
     */
    public Thread startWorker(AtomicInteger counter) {
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
     */
    public void shutdown() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void reset() {
        running = true;
    }
}
