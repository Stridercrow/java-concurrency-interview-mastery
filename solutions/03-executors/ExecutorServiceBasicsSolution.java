package com.concurrency.mastery.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SOLUTION — Exercise 09: ExecutorService Basics
 *
 * Key Concepts:
 * - ExecutorService abstracts thread management away from task submission
 * - Factory methods create different pool types:
 *   • newFixedThreadPool(n)    — n threads, tasks queue if all busy
 *   • newSingleThreadExecutor() — 1 thread, tasks execute sequentially
 *   • newCachedThreadPool()    — creates threads as needed, reuses idle ones
 * - submit() returns a Future for tracking completion and retrieving results
 * - Lifecycle: create → submit tasks → shutdown → awaitTermination
 *
 * Why use ExecutorService over raw threads:
 * - Reuses threads (no creation overhead per task)
 * - Manages task queue automatically
 * - Provides Future for result retrieval
 * - Clean shutdown with shutdown()/shutdownNow()
 */
public class ExecutorServiceBasicsSolution {

    /**
     * Create a fixed thread pool.
     * Reuses a fixed number of threads. Excess tasks wait in a LinkedBlockingQueue.
     */
    public ExecutorService createFixedPool(int poolSize) {
        return Executors.newFixedThreadPool(poolSize);
    }

    /**
     * Create a single-thread executor.
     * Tasks execute sequentially in the order they're submitted.
     * Useful for serializing access to a shared resource.
     */
    public ExecutorService createSingleThread() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * Submit a Runnable. Returns Future<Void> (completes with null).
     *
     * Use submit() instead of execute() when you need the Future for
     * tracking completion or catching exceptions.
     */
    public Future<?> submitRunnable(ExecutorService executor, Runnable task) {
        return executor.submit(task);
    }

    /**
     * Submit a Callable. Returns Future<T> with the task's result.
     *
     * The Future.get() method blocks until the result is available.
     */
    public <T> Future<T> submitCallable(ExecutorService executor, Callable<T> task) {
        return executor.submit(task);
    }

    /**
     * Check if shutdown has been called.
     *
     * isShutdown() returns true after shutdown() or shutdownNow() is called.
     * The executor may still be running submitted tasks.
     */
    public boolean isShutdown(ExecutorService executor) {
        return executor.isShutdown();
    }

    /**
     * Check if all tasks have completed after shutdown.
     *
     * isTerminated() returns true only after:
     * 1. shutdown() was called, AND
     * 2. All submitted tasks have completed
     */
    public boolean isTerminated(ExecutorService executor) {
        return executor.isTerminated();
    }
}
