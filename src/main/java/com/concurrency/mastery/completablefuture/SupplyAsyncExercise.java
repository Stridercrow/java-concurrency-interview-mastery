package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exercise 34 — supplyAsync()
 *
 * Scenario:
 * You're building an async data service. Operations should execute
 * without blocking the calling thread.
 *
 * CompletableFuture.supplyAsync() starts an asynchronous computation
 * that produces a value.
 */
public class SupplyAsyncExercise {

    /**
     * Start an asynchronous computation that returns a value.
     *
     * @param supplier the computation to run asynchronously
     * @return a CompletableFuture that will complete with the result
     */
    public <T> CompletableFuture<T> supplyAsync(java.util.function.Supplier<T> supplier) {
        // TODO: Start the supplier asynchronously using CompletableFuture.supplyAsync()
        // Return the CompletableFuture
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Start an asynchronous computation on a specific executor.
     *
     * @param supplier the computation to run asynchronously
     * @param executor the executor to run on
     * @return a CompletableFuture that will complete with the result
     */
    public <T> CompletableFuture<T> supplyAsyncOnExecutor(
            java.util.function.Supplier<T> supplier, ExecutorService executor) {
        // TODO: Start the supplier asynchronously on the given executor
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run an async computation and get the result, waiting up to the timeout.
     *
     * @param supplier the computation
     * @param timeoutMs the timeout in milliseconds
     * @return the result
     */
    public <T> T supplyAndGet(java.util.function.Supplier<T> supplier, long timeoutMs)
            throws Exception {
        // TODO: Start the supplier asynchronously, wait for the result with a timeout,
        // and return it. Use join() or get(timeout, unit).
        throw new UnsupportedOperationException("Implement this method");
    }
}
