package com.concurrency.mastery.completablefuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise 37 — Exception Handling")
class ExceptionHandlingExerciseTest {

    private ExceptionHandlingExercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new ExceptionHandlingExercise();
    }

    @Test
    @DisplayName("Should provide fallback on failure")
    void shouldProvideFallback() throws Exception {
        CompletableFuture<String> failing = CompletableFuture.failedFuture(
            new RuntimeException("boom"));

        CompletableFuture<String> result = exercise.withFallback(failing, "fallback");

        assertEquals("fallback", result.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Should keep original value on success")
    void shouldKeepValueOnSuccess() throws Exception {
        CompletableFuture<String> succeeding = CompletableFuture.completedFuture("success");

        CompletableFuture<String> result = exercise.withFallback(succeeding, "fallback");

        assertEquals("success", result.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Should handle both success and failure")
    void shouldHandleBothCases() throws Exception {
        CompletableFuture<String> success = CompletableFuture.completedFuture("data");
        CompletableFuture<String> failure = CompletableFuture.failedFuture(
            new RuntimeException("error"));

        CompletableFuture<String> result1 = exercise.handleResult(success,
            (val, ex) -> ex != null ? "error" : val.toUpperCase());

        CompletableFuture<String> result2 = exercise.handleResult(failure,
            (val, ex) -> ex != null ? "error" : val.toUpperCase());

        assertEquals("DATA", result1.get(5, TimeUnit.SECONDS));
        assertEquals("error", result2.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("should run action regardless of outcome")
    void shouldRunActionRegardless() throws Exception {
        AtomicBoolean ran = new AtomicBoolean(false);
        CompletableFuture<String> failing = CompletableFuture.failedFuture(
            new RuntimeException("boom"));

        exercise.withLogging(failing, (result, ex) -> ran.set(true));

        // Wait for the future to complete
        try { failing.join(); } catch (Exception e) { /* expected */ }
        Thread.sleep(100);
        assertTrue(ran.get(), "Action should run even on failure");
    }

    @Test
    @DisplayName("Resilient pipeline should handle valid input")
    void resilientPipelineShouldHandleValidInput() throws Exception {
        CompletableFuture<String> result = exercise.resilientPipeline("hello");
        assertEquals("Result: HELLO", result.get(5, TimeUnit.SECONDS));
    }

    @Test
    @DisplayName("Resilient pipeline should handle invalid input gracefully")
    void resilientPipelineShouldHandleInvalidInput() throws Exception {
        CompletableFuture<String> result = exercise.resilientPipeline("");
        assertEquals("Error occurred", result.get(5, TimeUnit.SECONDS));
    }
}
