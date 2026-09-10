package com.concurrency.mastery.interviewchallenges;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Challenge 1 — Background Worker Shutdown
 *
 * Problem:
 * Design a background worker that:
 * 1. Runs a periodic task every 100ms
 * 2. Can be gracefully shut down
 * 3. Handles interruption correctly
 * 4. Reports whether it is currently running
 *
 * Constraints:
 * - Do NOT use ExecutorService (use Thread directly)
 * - The worker must be interruptible
 * - Must handle InterruptedException correctly
 * - Must be reusable (can be started again after shutdown)
 *
 * Acceptance Criteria:
 * - Worker starts and runs periodically
 * - Worker stops gracefully when shutdown() is called
 * - Worker is interruptible
 * - isRunning() returns accurate status
 * - Worker can be restarted after shutdown
 *
 * Interview Discussion:
 * - Why use interruption for cancellation?
 * - What happens if you ignore InterruptedException?
 * - What is the difference between interrupt() and stop()?
 * - How does this compare to ExecutorService shutdown?
 */
public class BackgroundWorkerShutdown {

    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile Thread workerThread;

    /**
     * Start the background worker.
     * The worker should run the given task periodically until shutdown.
     *
     * @param task the task to run periodically
     */
    public void start(Runnable task) {
        // TODO: Create and start a worker thread that:
        // 1. Sets running to true
        // 2. Loops while running is true
        // 3. Executes the task in each iteration
        // 4. Sleeps for 100ms between iterations
        // 5. Catches InterruptedException, re-interrupts, and breaks
        // 6. Sets running to false in finally
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Gracefully shut down the worker.
     */
    public void shutdown() {
        // TODO: Set running to false and interrupt the worker thread
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check if the worker is currently running.
     */
    public boolean isRunning() {
        return running.get();
    }

    /**
     * Wait for the worker to stop.
     *
     * @param timeoutMs max time to wait
     * @return true if the worker stopped within the timeout
     */
    public boolean awaitShutdown(long timeoutMs) throws InterruptedException {
        // TODO: Wait for the worker thread to die within the timeout
        throw new UnsupportedOperationException("Implement this method");
    }
}
