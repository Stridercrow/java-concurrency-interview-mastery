package com.concurrency.mastery.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Exercise 10 — ExecutorService Lifecycle
 *
 * Scenario:
 * You're building a background task processor that needs to shut down gracefully
 * when the application stops. You need to understand executor lifecycle states
 * and interruption handling.
 */
public class ExecutorServiceLifecycle {

    /**
     * Perform a graceful shutdown of the executor:
     * 1. Call shutdown() to stop accepting new tasks
     * 2. Wait for existing tasks to complete
     * 3. If they don't complete within the timeout, call shutdownNow()
     *
     * @param executor the executor to shut down
     * @param timeoutMs max time to wait for graceful shutdown
     * @return true if shutdown was graceful, false if forced
     */
    public boolean gracefulShutdown(ExecutorService executor, long timeoutMs) throws InterruptedException {
        // TODO: Implement graceful shutdown sequence.
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Submit a long-running task and return a Future that can be cancelled.
     *
     * @param executor the executor
     * @param task the task to run
     * @return the Future representing the task
     */
    public <T> java.util.concurrent.Future<T> submitCancellableTask(
            ExecutorService executor, java.util.concurrent.Callable<T> task) {
        // TODO: Submit the task and return the Future
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run a task that checks for interruption periodically.
     * The task should set the flag to true when it starts, then
     * loop until interrupted, then set the flag to false.
     *
     * @param executor the executor
     * @param running flag that is true while the task is running
     */
    public void submitInterruptibleTask(ExecutorService executor, AtomicBoolean running) {
        // TODO: Submit a task that:
        // 1. Sets running to true
        // 2. Loops while not interrupted (Thread.currentThread().isInterrupted())
        //    with Thread.sleep(10) in each iteration
        // 3. Catches InterruptedException, re-interrupts the thread, and breaks
        // 4. Sets running to false in a finally block
        throw new UnsupportedOperationException("Implement this method");
    }
}
