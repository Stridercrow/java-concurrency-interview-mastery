package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * SOLUTION — Exercise 34: supplyAsync()
 *
 * Key Concepts:
 * - CompletableFuture.supplyAsync(Supplier) runs a computation asynchronously
 * - Returns immediately with a CompletableFuture (non-blocking)
 * - The Supplier runs on the ForkJoinPool common pool by default
 * - Use the overload with Executor to control which thread pool runs the task
 * - supplyAsync is the entry point for most async pipelines
 *
 * Execution model:
 *   main thread → calls supplyAsync() → returns immediately
 *                   ↓
 *   ForkJoinPool/common pool → runs the Supplier → completes the future
 *
 * Key methods:
 * - supplyAsync(supplier)           — runs on common pool
 * - supplyAsync(supplier, executor) — runs on specified executor
 * - join()                          — blocks for result (no checked exception)
 * - get(timeout, unit)              — blocks with timeout
 *
 * Common pitfall: Using ForkJoinPool.commonPool() for blocking I/O.
 * Always provide your own Executor for blocking operations.
 */
public class SupplyAsyncExerciseSolution {

    /**
     * Start an async computation on the common ForkJoinPool.
     *
     * The common pool has a default parallelism of Runtime.availableProcessors() - 1.
     * Good for CPU-bound tasks, NOT for blocking I/O.
     */
    public <T> CompletableFuture<T> supplyAsync(java.util.function.Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    /**
     * Start an async computation on a specific executor.
     *
     * This is the preferred approach in production:
     * - You control thread pool size
     * - You can use different pools for different task types
     * - Avoids starving the common pool
     */
    public <T> CompletableFuture<T> supplyAsyncOnExecutor(
            java.util.function.Supplier<T> supplier, ExecutorService executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    /**
     * Supply a value and get the result with a timeout.
     *
     * join() blocks until complete (throws CompletionException on error).
     * get(timeout, unit) blocks with a timeout (throws TimeoutException).
     */
    public <T> T supplyAndGet(java.util.function.Supplier<T> supplier, long timeoutMs)
            throws Exception {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(supplier);
        return future.get(timeoutMs, TimeUnit.MILLISECONDS);
    }
}
