package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exercise 38 — Custom Executor with CompletableFuture
 *
 * Scenario:
 * By default, CompletableFuture runs tasks on the ForkJoinPool.commonPool().
 * For production, you often need to control which thread pool handles your
 * async tasks.
 */
public class CustomExecutorExercise {

    /**
     * Create an ExecutorService with the given pool size.
     *
     * @param poolSize the number of threads
     * @return a new ExecutorService
     */
    public ExecutorService createExecutor(int poolSize) {
        // TODO: Create and return a fixed thread pool
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Supply a value asynchronously on the given executor.
     *
     * @param executor the executor to use
     * @param supplier the computation
     * @return a CompletableFuture with the result
     */
    public <T> CompletableFuture<T> supplyOn(ExecutorService executor,
                                              java.util.function.Supplier<T> supplier) {
        // TODO: Use CompletableFuture.supplyAsync(supplier, executor)
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run a multi-step async pipeline on a custom executor.
     * Step 1: Fetch user name (async)
     * Step 2: Transform to uppercase (async on same executor)
     * Step 3: Add greeting prefix (async on same executor)
     *
     * @param executor the executor to use for all steps
     * @param userId the user ID
     * @return a CompletableFuture with the greeting
     */
    public CompletableFuture<String> pipelineOnExecutor(ExecutorService executor, String userId) {
        // TODO: Create a 3-step async pipeline all running on the given executor.
        // 1. supplyAsync: return "user-" + userId
        // 2. thenApply: convert to uppercase
        // 3. thenApply: prepend "Hello, "
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Gracefully shut down an executor, waiting for tasks to complete.
     *
     * @param executor the executor to shut down
     * @param timeoutMs max time to wait
     * @return true if shutdown was graceful
     */
    public boolean shutdownGracefully(ExecutorService executor, long timeoutMs)
            throws InterruptedException {
        // TODO: Shutdown the executor and wait for termination
        throw new UnsupportedOperationException("Implement this method");
    }
}
