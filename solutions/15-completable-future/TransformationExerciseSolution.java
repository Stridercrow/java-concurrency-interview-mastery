package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * SOLUTION — Exercise 35: Transformation
 *
 * Key Concepts:
 * - thenApply(Function)     — transforms result (like Stream.map)
 * - thenAccept(Consumer)    — consumes result, returns void (like Stream.forEach)
 * - thenRun(Runnable)       — runs action after completion, ignores result
 *
 * All three return a new CompletableFuture (lazy, non-blocking).
 *
 * Functional analogy:
 * ┌──────────────────┬──────────────────────────────────────────┐
 * │ CompletableFuture│ Stream equivalent                       │
 * ├──────────────────┼──────────────────────────────────────────┤
 * │ thenApply(f)     │ stream.map(f)                           │
 * │ thenAccept(c)    │ stream.forEach(c)                       │
 * │ thenRun(r)       │ stream.peek(...) then terminal operation│
 * │ thenCompose(f)   │ stream.flatMap(f)                       │
 * └──────────────────┴──────────────────────────────────────────┘
 *
 * Execution model:
 *   CompletableFuture completes → callback runs asynchronously
 *   The callback runs on the thread that completed the future,
 *   OR on the executor if you use the thenApplyAsync overload.
 *
 * Chaining: each method returns a new CompletableFuture,
 * enabling fluent pipeline construction.
 */
public class TransformationExerciseSolution {

    /**
     * Transform a value using thenApply (like map).
     *
     * thenApply takes a Function<T, R> and returns CompletableFuture<R>.
     * The transformation runs after the input future completes.
     */
    public <T, R> CompletableFuture<R> applyTransformation(
            CompletableFuture<T> input, java.util.function.Function<T, R> transformer) {
        return input.thenApply(transformer);
    }

    /**
     * Consume a value using thenAccept (like forEach).
     *
     * thenAccept takes a Consumer<T> and returns CompletableFuture<Void>.
     * Useful for side effects like logging or updating UI.
     */
    public <T> CompletableFuture<Void> acceptResult(
            CompletableFuture<T> input, java.util.function.Consumer<T> consumer) {
        return input.thenAccept(consumer);
    }

    /**
     * Run a side effect after completion using thenRun.
     *
     * thenRun takes a Runnable and returns CompletableFuture<Void>.
     * The action doesn't receive the result — it just runs after completion.
     * Useful for cleanup, logging, or triggering downstream work.
     */
    public CompletableFuture<Void> runAfterCompletion(
            CompletableFuture<?> input, Runnable action) {
        return input.thenRun(action);
    }

    /**
     * Chain multiple transformations in a fluent pipeline.
     *
     * Each thenApply returns a new CompletableFuture, enabling chaining.
     * The pipeline is lazy — nothing executes until the input completes.
     */
    public CompletableFuture<String> chainTransformations(CompletableFuture<Integer> input) {
        return input
            .thenApply(n -> n * 2)           // Integer → Integer
            .thenApply(String::valueOf)       // Integer → String
            .thenApply(s -> "result " + s);  // String → String
    }
}
