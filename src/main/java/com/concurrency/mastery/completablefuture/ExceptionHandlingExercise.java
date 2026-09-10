package com.concurrency.mastery.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * Exercise 37 — Exception Handling
 *
 * Scenario:
 * Async operations can fail. You need to handle exceptions gracefully
 * without crashing the entire pipeline.
 *
 * Practice exceptionally, handle, and whenComplete.
 */
public class ExceptionHandlingExercise {

    /**
     * Handle exceptions using exceptionally — provide a fallback value.
     *
     * @param input the async computation that might fail
     * @param fallback the fallback value if the computation fails
     * @return a CompletableFuture that never fails (always has a value)
     */
    public <T> CompletableFuture<T> withFallback(
            CompletableFuture<T> input, T fallback) {
        // TODO: Use exceptionally to return the fallback when the input fails
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Handle both success and failure using handle.
     *
     * @param input the async computation
     * @param successHandler function to transform the result on success
     * @param errorHandler function to handle the exception on failure
     * @return a CompletableFuture with the handled result
     */
    public <T, R> CompletableFuture<R> handleResult(
            CompletableFuture<T> input,
            java.util.function.BiFunction<T, Throwable, R> successHandler) {
        // TODO: Use handle to process both success and failure cases
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Run a side effect regardless of success or failure using whenComplete.
     *
     * @param input the async computation
     * @param action the action to run (receives result and/or exception)
     * @return the original CompletableFuture (unchanged)
     */
    public <T> CompletableFuture<T> withLogging(
            CompletableFuture<T> input,
            java.util.function.BiConsumer<T, Throwable> action) {
        // TODO: Use whenComplete to run the action after completion
        throw new UnsupportedOperationException("Implement this method");
    }

    /**
     * Chain operations where the second operation might also fail.
     * Handle exceptions at each stage.
     *
     * @param input the initial value
     * @return a CompletableFuture with the result or a fallback
     */
    public CompletableFuture<String> resilientPipeline(String input) {
        // TODO: Create a pipeline that:
        // 1. Validates input (throws if null or blank)
        // 2. Transforms to uppercase
        // 3. Adds prefix "Result: "
        // 4. Handle any exception with a fallback "Error occurred"
        throw new UnsupportedOperationException("Implement this method");
    }
}
