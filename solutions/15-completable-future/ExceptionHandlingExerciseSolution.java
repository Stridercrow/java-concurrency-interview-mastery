package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * SOLUTION — Exercise 37: Exception Handling
 *
 * Key Concepts:
 * - exceptionally: provide fallback on failure
 * - handle: process both success and failure
 * - whenComplete: run side effect after completion
 * - Exception propagation in chains
 */
public class ExceptionHandlingExerciseSolution {

    public <T> CompletableFuture<T> withFallback(CompletableFuture<T> input, T fallback) {
        return input.exceptionally(ex -> fallback);
    }

    public <T, R> CompletableFuture<R> handleResult(
            CompletableFuture<T> input,
            java.util.function.BiFunction<T, Throwable, R> successHandler) {
        return input.handle(successHandler);
    }

    public <T> CompletableFuture<T> withLogging(
            CompletableFuture<T> input,
            java.util.function.BiConsumer<T, Throwable> action) {
        return input.whenComplete(action);
    }

    public CompletableFuture<String> resilientPipeline(String input) {
        return CompletableFuture.supplyAsync(() -> {
            if (input == null || input.isBlank()) {
                throw new IllegalArgumentException("Invalid input");
            }
            return input;
        })
        .thenApply(String::toUpperCase)
        .thenApply(s -> "Result: " + s)
        .exceptionally(ex -> "Error occurred");
    }
}
