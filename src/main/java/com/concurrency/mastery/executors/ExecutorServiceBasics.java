package com.concurrency.mastery.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Exercise 09 — ExecutorService Basics
 *
 * Scenario:
 * You're building a task execution framework. You need to understand
 * how to submit tasks (both Runnable and Callable), retrieve results
 * via Future, and manage executor lifecycle.
 */
public class ExecutorServiceBasics {

    /**
     * Create a fixed thread pool with the given number of threads.
     *
     * @param poolSize number of threads
     * @return a new ExecutorService
     */
    public ExecutorService createFixedPool(int poolSize) {
        // TODO: Create and return a fixed thread pool
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Create a single-thread executor.
     *
     * @return a new ExecutorService
     */
    public ExecutorService createSingleThread() {
        // TODO: Create and return a single-thread executor
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Submit a Runnable task to the executor.
     * Return the Future (which will complete with null).
     *
     * @param executor the executor service
     * @param task the runnable task
     * @return the Future representing the task
     */
    public Future<?> submitRunnable(ExecutorService executor, Runnable task) {
        // TODO: Submit the Runnable and return the Future
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Submit a Callable task to the executor and return the Future.
     *
     * @param executor the executor service
     * @param task the callable task
     * @return the Future representing the task with its result
     */
    public <T> Future<T> submitCallable(ExecutorService executor, Callable<T> task) {
        // TODO: Submit the Callable and return the Future
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check if the executor has been shut down.
     *
     * @param executor the executor service
     * @return true if shutdown has been called
     */
    public boolean isShutdown(ExecutorService executor) {
        // TODO: Return whether the executor has been shut down
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Check if the executor has terminated.
     *
     * @param executor the executor service
     * @return true if all tasks have completed after shutdown
     */
    public boolean isTerminated(ExecutorService executor) {
        // TODO: Return whether the executor has terminated
        throw new UnsupportedOperationException("Implement this method");
    }
}
