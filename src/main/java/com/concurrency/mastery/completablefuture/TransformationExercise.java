package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * Exercise 35 — Transformation
 *
 * Scenario:
 * You're building an async data pipeline. Results from one computation
 * are transformed by subsequent operations.
 *
 * Practice thenApply, thenAccept, and thenRun.
 */
public class TransformationExercise {

    /**
     * Transform a value using thenApply (like map).
     *
     * @param input the initial async computation
     * @param transformer the transformation function
     * @return a CompletableFuture with the transformed value
     */
    public <T, R> CompletableFuture<R> applyTransformation(
            CompletableFuture<T> input, java.util.function.Function<T, R> transformer) {
        // TODO: Use thenApply to transform the value
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Consume a value using thenAccept (like forEach).
     *
     * @param input the initial async computation
     * @param consumer the consumer function
     * @return a CompletableFuture that completes after the consumer runs
     */
    public <T> CompletableFuture<Void> acceptResult(
            CompletableFuture<T> input, java.util.function.Consumer<T> consumer) {
        // TODO: Use thenAccept to consume the value
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run a side effect after completion using thenRun.
     *
     * @param input the initial async computation
     * @param action the action to run after completion
     * @return a CompletableFuture that completes after the action runs
     */
    public CompletableFuture<Void> runAfterCompletion(
            CompletableFuture<?> input, Runnable action) {
        // TODO: Use thenRun to run an action after completion
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Chain multiple transformations.
     *
     * @param input the initial value
     * @return a CompletableFuture with the final transformed value
     */
    public CompletableFuture<String> chainTransformations(CompletableFuture<Integer> input) {
        // TODO: Chain the following transformations:
        // 1. thenApply: multiply by 2
        // 2. thenApply: convert to string
        // 3. thenApply: add " result" prefix
        throw new UnsupportedOperationException("Implement this method");
    }
}
