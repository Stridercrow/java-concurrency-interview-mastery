package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * SOLUTION — Exercise 38: Custom Executor with CompletableFuture
 *
 * Key Concepts:
 * - CompletableFuture.supplyAsync(supplier, executor) runs on custom pool
 * - ForkJoinPool.commonPool() is the default — not suitable for blocking I/O
 * - Always shut down custom executors when done
 */
public class CustomExecutorExerciseSolution {

    public ExecutorService createExecutor(int poolSize) {
        return Executors.newFixedThreadPool(poolSize);
    }

    public <T> CompletableFuture<T> supplyOn(ExecutorService executor,
                                              java.util.function.Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public CompletableFuture<String> pipelineOnExecutor(ExecutorService executor, String userId) {
        return CompletableFuture.supplyAsync(() -> "user-" + userId, executor)
            .thenApply(String::toUpperCase)
            .thenApply(s -> "Hello, " + s);
    }

    public boolean shutdownGracefully(ExecutorService executor, long timeoutMs)
            throws InterruptedException {
        executor.shutdown();
        return executor.awaitTermination(timeoutMs, TimeUnit.MILLISECONDS);
    }
}
